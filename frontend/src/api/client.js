import axios from 'axios'

export const api = axios.create({
    baseURL: '/v1',
    headers: { 'Content-Type': 'application/json' },
})

export const endpoints = {
    login: '/auth/loginG',
    register: '/auth/registerG',
    quadras: '/quadra/getAll',
    quadraById: (id) => `/quadra/getById/${id}`,
    quadraByNome: (nome) => `/quadra/getByNome/${nome}`,
    createQuadra: '/quadra/create',
    updateQuadra: (id) => `/quadra/update/${id}`,
    deleteQuadra: (id) => `/quadra/delete/${id}`,
}

// Interceptor: Token JWT
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')
    if (token) config.headers.Authorization = `Bearer ${token}`
    return config
})

// 401 → logout
api.interceptors.response.use(
    (res) => res,
    (error) => {
        const isAuthRoute = error.config?.url?.startsWith('/auth')
        if (error.response?.status === 401 && !isAuthRoute) {
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)