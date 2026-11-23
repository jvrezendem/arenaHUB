export default function Logo({ tone = 'dark' }) {
    return (
        <div className={`logo logo--${tone}`}>
            <span className="logo__mark">
                arena<span className="logo__hub">hub</span>
            </span>
            <span className="logo__tag">ESPORTES DE AREIA</span>
        </div>
    )
}
