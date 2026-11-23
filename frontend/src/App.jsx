
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import Login from './pages/Login/Login';
import CriarContaGestor from './pages/CriarContaGestor/CriarContaGestor';

function PrivateRoute({ children }) {
  const { isAuthenticated } = useAuth();

  return isAuthenticated
    ? children
    : <Navigate to="/login" replace />;
}

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/primeiro-acesso" element={<CriarContaGestor />} />

      <Route
        path="/"
        element={
          <PrivateRoute>
            <h1>Bem-vindo ao ArenaHUB!</h1>
          </PrivateRoute>
        }
      />

      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </BrowserRouter>
  );
}
