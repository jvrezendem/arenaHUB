
import { useState } from 'react';
import { Card, Label, TextInput, Button, Alert } from 'flowbite-react';
import { useAuth } from '../../contexts/AuthContext';
import './Login.css';

function Login() {
    const { login } = useAuth();

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [carregando, setCarregando] = useState(false);
    const [senhaVisivel, setSenhaVisivel] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setErro('');
        setCarregando(true);

        try {
            await login(email, senha);
        } catch (error) {
            setErro(
                error.response?.data?.message ||
                'Não foi possível realizar o login. Verifique suas credenciais.'
            );
        } finally {
            setCarregando(false);
        }
    }

    return (
        <main className="login-container">

            <section className="login-brand">
                <div className="brand-decoration brand-circle-one"></div>
                <div className="brand-decoration brand-circle-two"></div>

                <div className="brand-content">
                    <h1>Arena<span>HUB</span></h1>
                    <p>Seu espaço para jogar, conectar e competir.</p>
                </div>
            </section>

            <section className="login-content">
                <Card className="login-card">

                    <h2 className="login-title">Bem-vindo ao ArenaHUB</h2>

                    <p className="login-subtitle">
                        Entre na sua conta para continuar.
                    </p>

                    {erro && (
                        <Alert color="failure">
                            {erro}
                        </Alert>
                    )}

                    <form onSubmit={handleSubmit} className="login-form">

                        <div>
                            <Label htmlFor="email">E-mail</Label>
                            <TextInput
                                id="email"
                                type="email"
                                placeholder="seu@email.com"
                                value={email}
                                onChange={(event) => setEmail(event.target.value)}
                                required
                            />
                        </div>


                        <div className="password-field">
                            <Label htmlFor="senha">Senha</Label>

                            <div className="password-input-wrapper">
                                <TextInput
                                    id="senha"
                                    type={senhaVisivel ? 'text' : 'password'}
                                    placeholder="Digite sua senha"
                                    value={senha}
                                    onChange={(event) => setSenha(event.target.value)}
                                    required
                                />

                                <button
                                    type="button"
                                    className="password-toggle"
                                    onClick={() => setSenhaVisivel(!senhaVisivel)}
                                >
                                    {senhaVisivel ? 'Ocultar' : 'Mostrar'}
                                </button>
                            </div>
                        </div>


                        <Button
                            type="submit"
                            disabled={carregando}
                            className="login-button"
                        >
                            {carregando ? 'Entrando...' : 'Entrar'}
                        </Button>

                    </form>
                </Card>
            </section>

        </main>
    );
}

export default Login;
