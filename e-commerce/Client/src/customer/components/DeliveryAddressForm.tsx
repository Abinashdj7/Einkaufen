import Grid from "@mui/material/Grid"
import { AddressCard } from "./AddressCard"
import { Box, Button, TextField } from "@mui/material"
import { useDispatch } from "react-redux"
import { createOrder } from "./Action"
import { useNavigate } from "react-router-dom"
import { useState } from "react"

export const DeliveryAddressForm = () => {
    const [address,setAddress]=useState()
    const navigate=useNavigate()
    const dispatch=useDispatch()
    const handleSubmit=(e:any) => {
        e.preventDefault()
        const data=new FormData(e.currentTarget)
        const address_data={
            firstName:data.get("firstname"),
            lastName:data.get("lastname"),
            address:data.get("address"),
            city:data.get("city"),
            state:data.get("state"),
            zip:data.get("zip"),
            phoneNumber:data.get("phonenumber")
        }
        const passable_data={address:address_data,navigate:navigate}
        dispatch(createOrder(passable_data))
        setAddress(address)
    }
    return (<div>
        <Grid container spacing={4}>
            <Grid xs={12} lg={5} className="border rounded-e-md shadow-md h-[30.5rem] overflow-y-scroll">
                <div className="p-5 py-7 border-b cursor-pointer" >
                    <AddressCard address={address}/>
                </div>
            </Grid>
            <Grid item xs={12} lg={7}>
                <Box className="border rounded-s-md shadow-md p-5">
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={3}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required id="firstname" name="firstname"
                                    label="Firstname" fullWidth autoComplete="given-name"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required id="lastname" name="lastname"
                                    label="Lastname" fullWidth autoComplete="given-name"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required id="address" name="address"
                                    label="Address" fullWidth autoComplete="given-name"
                                    multiline rows={4}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required id="city" name="city"
                                    label="City" fullWidth autoComplete="given-name"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required id="state" name="state"
                                    label="State/Province/Region" fullWidth autoComplete="given-name"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required id="zip" name="zip"
                                    label="Zip/Postal code" fullWidth autoComplete="shipping postal-code"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required id="phonenumber" name="phonenumber"
                                    label="Phone number" fullWidth autoComplete="given-name"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                            <Button sx={{py:1.5, mt: 2, bgcolor: "RGB(145 85 253)" }} size="large" variant="contained" type="submit">Deliver here</Button>
                            </Grid>
                        </Grid>
                    </form>
                </Box>
            </Grid>
        </Grid>
    </div>)
}