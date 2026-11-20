// src/App.jsx
import { BrowserRouter, Routes, Route, Navigate, Outlet } from 'react-router-dom'
import { AuthProvider, useAuth } from './contexts/AuthContext'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Quadras from './pages/Quadras'
import MinhasAulas from './pages/MinhasAulas'

function PrivateRoute({ children }) {
  const { isAuthenticated } = useAuth()
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />
}

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route element={<PrivateRoute><Dashboard /></PrivateRoute>}>
        <Route path="/" element={<Navigate to="/quadras" replace />} />
        <Route path="quadras" element={<Quadras />} />
        <Route path="minhas-aulas" element={<MinhasAulas />} />
      </Route>
      <Route path="*" element={<Navigate to="/quadras" />} />
    </Routes>
  )
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </BrowserRouter>
  )
}