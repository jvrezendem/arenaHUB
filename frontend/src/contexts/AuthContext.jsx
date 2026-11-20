// src/contexts/AuthContext.jsx
import { createContext, useContext, useState, useEffect } from 'react'
import { api, endpoints } from '../api/client'
import { getEmailFromToken, isTokenExpired } from '../utils/jwt'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const token = localStorage.getItem('token')
        const storedUser = localStorage.getItem('user')

        if (token && storedUser) {

            if (isTokenExpired(token)) {
                localStorage.removeItem('token')
                localStorage.removeItem('user')
            } else {
                setUser(JSON.parse(storedUser))
            }
        }
        setLoading(false)
    }, [])

    const login = async (email, senha) => {
        const res = await api.post(endpoints.login, { email, senha })
        const { token, expiration } = res.data

        const tokenEmail = getEmailFromToken(token)

        const usuario = {
            email: tokenEmail || email,
            nome: tokenEmail?.split('@')[0] || 'Usuário',
            token,
        }

        localStorage.setItem('token', token)
        localStorage.setItem('user', JSON.stringify(usuario))
        setUser(usuario)
    }

    const logout = () => {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        setUser(null)
    }

    if (loading) return null  // ou <LoadingSpinner />

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