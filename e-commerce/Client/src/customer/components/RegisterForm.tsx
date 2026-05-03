import { Button, Grid, TextField } from "@mui/material"
import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"
import { getUser, register } from "./Action"
import { useEffect } from "react";

interface UserData {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export const RegisterForm = () => {
    const navigate=useNavigate()
    const dispatch=useDispatch()
    const handleSubmit = (event) => {
        event.preventDefault()
        const data = new FormData(event.currentTarget)
        const userData : UserData = {
            firstName: data.get("firstName") as string,
            lastName: data.get("lastName") as string,
            email: data.get("email") as string,
            password: data.get("password") as string
        }
        dispatch(register(userData))
    }
    return (<>
        <form onSubmit={handleSubmit}>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <TextField required id="first-name"
                        name="firstName" label="First name"
                        fullWidth autoComplete="given-name"
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required id="last-name"
                        name="lastName" label="Last name"
                        fullWidth autoComplete="given-name"
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField required id="email"
                        name="email" label="Email"
                        fullWidth autoComplete="email"
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField required id="password"
                        name="password" label="Password"
                        fullWidth autoComplete="password"
                    />
                </Grid>
                <Grid item xs={12}>
                    <Button
                        className="bg-[#9155FD] w-full"
                        type="submit" variant="contained"
                        size="large"
                        sx={{ padding: ".8rem 0", bgcolor: "#9155FD" }}
                    >Register</Button>
                </Grid>
            </Grid>
        </form>
        <div className="flex justify-center flex-col items-center">
            <div className="py-3 flex items-center">
                <p>If you already have an account</p>
                <Button onClick={() => navigate("/login")}
                className="ml-5" size="small"
                >Login</Button>
            </div>
        </div>
    </>)
}