import { AlertIcon } from './icons'

export default function Field({ id, label, hint, error, ...inputProps }) {
    const messageId = error ? `${id}-erro` : hint ? `${id}-dica` : undefined

    return (
        <div className="field">
            <label htmlFor={id} className="field__label">{label}</label>

            <input
                id={id}
                className={`field__input${error ? ' field__input--error' : ''}`}
                aria-invalid={error ? 'true' : undefined}
                aria-describedby={messageId}
                {...inputProps}
            />

            {error ? (
                <p id={messageId} className="field__error" role="alert">
                    <AlertIcon />
                    {error}
                </p>
            ) : hint ? (
                <p id={messageId} className="field__hint">{hint}</p>
            ) : null}
        </div>
    )
}
