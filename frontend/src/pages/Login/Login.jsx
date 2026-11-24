import { useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import AuthShell from '../../components/AuthShell'
import Field from '../../components/Field'
import { InfoIcon } from '../../components/icons'
import { useAuth } from '../../contexts/AuthContext'
import { useGestorExiste } from '../../hooks/useGestorExiste'

function validar({ email, senha }) {
    const erros = {}
    if (!email.trim()) erros.email = 'Informe o e-mail para entrar.'
    if (!senha) erros.senha = 'Informe a senha para entrar.'
    return erros
}

export default function Login() {
    const { login } = useAuth()
    const gestorExiste = useGestorExiste()
    const navigate = useNavigate()
    const location = useLocation()

    const [email, setEmail] = useState('')
    const [senha, setSenha] = useState('')
    const [erros, setErros] = useState({})
    const [erroGeral, setErroGeral] = useState('')
    const [carregando, setCarregando] = useState(false)

    const contaCriada = location.state?.contaCriada === true

    async function handleSubmit(event) {
        event.preventDefault()
        setErroGeral('')

        const novosErros = validar({ email, senha })
        setErros(novosErros)
        if (Object.keys(novosErros).length > 0) return

        setCarregando(true)
        try {
            await login(email.trim(), senha)
            navigate('/', { replace: true })
        } catch (error) {
            setErroGeral(
                error.response?.status === 401
                    ? 'E-mail ou senha incorretos.'
                    : 'Não foi possível entrar agora. Tente novamente.'
            )
        } finally {
            setCarregando(false)
        }
    }

    return (
        <AuthShell>
            <h1 className="auth-title" style={{ marginBottom: 16 }}>Entrar</h1>

            {contaCriada && (
                <div className="notice notice--success" role="status">
                    <InfoIcon />
                    Conta criada com sucesso. Entre para continuar.
                </div>
            )}

            {erroGeral && (
                <div className="notice notice--error" role="alert">
                    <InfoIcon />
                    {erroGeral}
                </div>
            )}

            <form className="auth-form" onSubmit={handleSubmit} noValidate>
                <Field
                    id="email"
                    label="E-mail"
                    type="email"
                    autoComplete="username"
                    placeholder="Digite seu e-mail"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    error={erros.email}
                />

                <Field
                    id="senha"
                    label="Senha"
                    type="password"
                    autoComplete="current-password"
                    placeholder="Digite sua senha"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                    error={erros.senha}
                />

                <div className="auth-actions">
                    <button type="submit" className="btn btn--primary" disabled={carregando}>
                        {carregando ? 'Entrando...' : 'Entrar'}
                    </button>
                </div>
            </form>

            {!gestorExiste && (
                <p className="auth-switch">
                    Primeiro acesso?{' '}
                    <Link to="/primeiro-acesso" className="auth-link">Criar conta</Link>
                </p>
            )}
            <p className="auth-note">
                Versão de teste: use o e-mail e a senha informados pelo facilitador.
            </p>
        </AuthShell>
    )
}
