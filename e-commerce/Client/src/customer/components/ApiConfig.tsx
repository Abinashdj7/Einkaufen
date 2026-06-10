import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios"

export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"

export const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json"
    }
})

if (import.meta.env.DEV) {
    api.interceptors.request.use(
        (config: AxiosRequestConfig) => {
            console.log('[API REQUEST]', config.method?.toUpperCase(), config.url)
            return config;
        },
        (error: AxiosError) => {
            console.error('[API REQUEST ERROR]', error)
            return Promise.reject(error);
        }
    );

    api.interceptors.response.use(
        (response: AxiosResponse) => {
            console.log('[API RESPONSE]', response.status, response.config.method?.toUpperCase(), response.config.url, response.data?.length ? `(${response.data.length} items)` : '')
            return response;
        },
        (error: AxiosError) => {
            console.error('[API RESPONSE ERROR]', error.response?.status, error.config?.method?.toUpperCase(), error.config?.url, error.message)
            return Promise.reject(error);
        }
    );
}
