// vite.config.js
import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'
import tailwindcss from "@tailwindcss/vite";
import flowbiteReact from "flowbite-react/plugin/vite";

export default defineConfig({
  plugins: [react(), tailwindcss(), flowbiteReact()],
  server: {
    port: 5173,
    strictPort: true,
    host: true,
    proxy: {
      "/v1": {
        target: "http://localhost:8081",
        changeOrigin: true,
      },
    }
  }
})