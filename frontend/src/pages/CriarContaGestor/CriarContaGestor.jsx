import { useState } from 'react'
import { Link, Navigate, useNavigate } from 'react-router-dom'
import { api, endpoints } from '../../api/client'
import AuthShell from '../../components/AuthShell'
import Field from '../../components/Field'
import { ArrowLeftIcon, InfoIcon } from '../../components/icons'
import { useAuth } from '../../contexts/AuthContext'
import { useGestorExiste } from '../../hooks/useGestorExiste'

const SENHA_FORTE = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).+$/
const EMAIL_VALIDO = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const DICA_SENHA =
    'Mínimo de 8 caracteres, com maiúscula, minúscula, número e caractere especial (@ $ ! % * ? &).'

function validar({ nome, email, senha, confirmacao }) {
    const erros = {}

    if (!nome.trim()) erros.nome = 'Preencha este campo.'

    if (!email.trim()) erros.email = 'Preencha este campo.'
    else if (!EMAIL_VALIDO.test(email.trim())) erros.email = 'Informe um e-mail válido.'

    if (!senha) erros.senha = 'Preencha este campo.'
    else if (senha.length < 8) erros.senha = 'A senha precisa ter pelo menos 8 caracteres.'
    else if (!SENHA_FORTE.test(senha))
        erros.senha = 'A senha precisa ter maiúscula, minúscula, número e caractere especial (@ $ ! % * ? &).'

    if (!confirmacao) erros.confirmacao = 'Preencha este campo.'
    else if (confirmacao !== senha) erros.confirmacao = 'As senhas não conferem.'

    return erros
}

export default function CriarContaGestor() {
    const navigate = useNavigate()
    const { login } = useAuth()
    const gestorExiste = useGestorExiste()

    const [form, setForm] = useState({ nome: '', email: '', senha: '', confirmacao: '' })
    const [erros, setErros] = useState({})
    const [erroGeral, setErroGeral] = useState('')
    const [carregando, setCarregando] = useState(false)

    const atualizar = (campo) => (event) =>
        setForm((anterior) => ({ ...anterior, [campo]: event.target.value }))

    async function handleSubmit(event) {
        event.preventDefault()
        setErroGeral('')

        const novosErros = validar(form)
        setErros(novosErros)
        if (Object.keys(novosErros).length > 0) return

        setCarregando(true)
        try {
            await api.post(endpoints.register, {
                nome: form.nome.trim(),
                email: form.email.trim(),
                senha: form.senha,
            })

            try {
                await login(form.email.trim(), form.senha)
                navigate('/', { replace: true })
            } catch {
                navigate('/login', { replace: true, state: { contaCriada: true } })
            }
        } catch (error) {
            const status = error.response?.status

            if (status === 409) {
                setErros({ email: error.response.data?.msg || 'Este e-mail já está cadastrado.' })
            } else if (status === 403) {
                setErroGeral(error.response.data?.msg || 'Já existe um gestor cadastrado.')
            } else if (status === 400) {
                setErroGeral('Verifique os dados informados e tente novamente.')
            } else {
                setErroGeral('Não foi possível criar a conta agora. Tente novamente.')
            }
        } finally {
            setCarregando(false)
        }
    }

    if (gestorExiste) return <Navigate to="/login" replace />

    return (
        <AuthShell variant="form">
            <div className="auth-header">
                <Link to="/login" className="auth-back" aria-label="Voltar para o login">
                    <ArrowLeftIcon />
                </Link>
                <h1 className="auth-title">Criar conta do gestor</h1>
            </div>

            {erroGeral && (
                <div className="notice notice--error" role="alert">
                    <InfoIcon />
                    {erroGeral}
                </div>
            )}

            <form className="auth-form" onSubmit={handleSubmit} noValidate>
                <Field
                    id="nome"
                    label="Nome"
                    autoComplete="name"
                    placeholder="Digite seu nome"
                    value={form.nome}
                    onChange={atualizar('nome')}
                    error={erros.nome}
                />

                <Field
                    id="email"
                    label="E-mail"
                    type="email"
                    autoComplete="email"
                    placeholder="Digite seu e-mail"
                    value={form.email}
                    onChange={atualizar('email')}
                    error={erros.email}
                />

                <Field
                    id="senha"
                    label="Senha"
                    type="password"
                    autoComplete="new-password"
                    placeholder="Digite uma senha"
                    hint={DICA_SENHA}
                    value={form.senha}
                    onChange={atualizar('senha')}
                    error={erros.senha}
                />

                <Field
                    id="confirmacao"
                    label="Confirmar senha"
                    type="password"
                    autoComplete="new-password"
                    placeholder="Digite a senha de novo"
                    value={form.confirmacao}
                    onChange={atualizar('confirmacao')}
                    error={erros.confirmacao}
                />

                <div className="auth-actions">
                    <button type="submit" className="btn btn--primary" disabled={carregando}>
                        {carregando ? 'Criando conta...' : 'Criar conta'}
                    </button>
                    <Link to="/login" className="btn btn--outline">Voltar</Link>
                </div>
            </form>
        </AuthShell>
    )
}
