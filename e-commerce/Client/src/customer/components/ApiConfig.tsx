import axios from "axios"

export const API_BASE_URL = "http://localhost:8080"
// Log all API requests and responses for debugging
// console.log("[API_CONFIG] Initializing API with base URL:", API_BASE_URL);

export const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json"
    }
})

// Add request interceptor for logging (disabled)
api.interceptors.request.use(
    (config) => {
        // console.log("[API_CONFIG] >>> REQUEST:", config.method?.toUpperCase(), config.url);
        return config;
    },
    (error) => {
        // console.error("[API_CONFIG] >>> REQUEST ERROR:", error.message);
        return Promise.reject(error);
    }
);

// Add response interceptor for logging (disabled)
api.interceptors.response.use(
    (response) => {
        // console.log("[API_CONFIG] <<< RESPONSE:", response.status, response.config.url, "- Data length:", Array.isArray(response.data) ? response.data.length : "N/A");
        return response;
    },
    (error) => {
        // console.error("[API_CONFIG] <<< RESPONSE ERROR:", error.message, "- URL:", error.config?.url);
        return Promise.reject(error);
    }
);
