import Logo from './Logo'
import '../styles/auth.css'

export default function AuthShell({ variant = 'default', children }) {
    return (
        <main className={`auth auth--${variant}`}>
            <aside className="auth-brand" aria-hidden="true">
                <span className="auth-brand__sun" />
                <span className="auth-brand__dot" />
                <span className="auth-brand__wave" />
                <div className="auth-brand__logo">
                    <Logo tone="light" />
                </div>
            </aside>

            <section className="auth-content">
                <span className="auth-blob auth-blob--warm" />
                <span className="auth-blob auth-blob--cool" />
                <div className="auth-mobile-logo">
                    <Logo tone="dark" />
                </div>
                <div className="auth-card">{children}</div>
            </section>
        </main>
    )
}
