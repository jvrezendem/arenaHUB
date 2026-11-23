import { useState } from 'react'
import { api, endpoints } from '../api/client'
import { getEmailFromToken, isTokenExpired } from '../utils/jwt'
import { AuthContext } from './AuthContext'

function carregarUsuarioSalvo() {
    const token = localStorage.getItem('token')
    const usuarioSalvo = localStorage.getItem('user')

    if (!token || !usuarioSalvo) return null

    if (isTokenExpired(token)) {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        return null
    }

    return JSON.parse(usuarioSalvo)
}

export function AuthProvider({ children }) {
    const [user, setUser] = useState(carregarUsuarioSalvo)

    const login = async (email, senha) => {
        const res = await api.post(endpoints.login, { email, senha })
        const { token } = res.data

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

    return (
        <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}
