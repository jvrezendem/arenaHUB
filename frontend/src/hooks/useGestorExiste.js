import { useEffect, useState } from 'react'
import { api, endpoints } from '../api/client'

// null = ainda não sabemos (carregando ou falha na consulta); true/false = resposta da API
export function useGestorExiste() {
    const [existe, setExiste] = useState(null)

    useEffect(() => {
        let ativo = true

        api.get(endpoints.gestorExiste)
            .then((res) => {
                if (ativo) setExiste(res.data.existe)
            })
            .catch(() => {})

        return () => {
            ativo = false
        }
    }, [])

    return existe
}
