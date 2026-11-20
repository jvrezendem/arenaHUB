// src/contexts/AuthContext.jsx
import { createContext, useContext, useState, useEffect } from 'react'
import { api } from '../api/client'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const stored = localStorage.getItem('user')
        if (stored) setUser(JSON.parse(stored))
        setLoading(false)
    }, [])

    const login = async (email, senha) => {
        const res = await api.post('/auth/login', { email, senha })
        const { token, usuario } = res.data
        localStorage.setItem('token', token)
        localStorage.setItem('user', JSON.stringify(usuario))
        setUser(usuario)
    }

    const logout = () => {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        setUser(null)
    }

    if (loading) return null // ou <LoadingSpinner />

    return (
        <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth() {
    const ctx = useContext(AuthContext)
    if (!ctx) throw new Error('useAuth deve ser usado dentro de AuthProvider')
    return ctx
}