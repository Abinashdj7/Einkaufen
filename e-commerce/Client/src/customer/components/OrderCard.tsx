import { Grid } from "@mui/material"
import { Adjust } from "@mui/icons-material"
import { useNavigate } from "react-router-dom"

export const OrderCard = () => {
    const navigate=useNavigate()
    return (<div onClick={() => navigate(`/account/order/${5}`)} className="p-5 shadow-md shadow-black hover:shadow-2xl border">
        <Grid container spacing={2} sx={{ justifyContent: "space-between" }}>
            <Grid item xs={6}>
                <div className="flex cursor-pointer">
                    <img className="w-[5rem] h-[5rem] object-cover object-top" src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhUPAZQj5WiQHGyFbWhtVV517Ocn4nu4nrL1FTvQQ71QT36tInie2rk9BlFgUHDV_jSic18JQCwZwZtwgEDX3U_npddwIfYw1XYhsa_v20Iy7q9kKiwiejCeUlXvXdm0S9DjO08ioLeWzJV/s1600/0eb298aae08a44e33618569d4b1ca74f.jpg" />
                    <div className="ml-5 space-y-2">
                        <p className="">Another red saree</p>
                        <p className="opacity-50 text-xs font-semibold">Size : M</p>
                        <p className="opacity-50 text-xs font-semibold">Colour : Black</p>
                    </div>
                </div>
            </Grid>
            <Grid item xs={2}>
                <p>$34</p>
            </Grid>
            <Grid item xs={4}>
                {true && <div><p>
                    <Adjust sx={{ width: "15px", height: "15px" }} className="text-green-600 mr-2 text-sm" />
                    <span>Delivered on March 3</span>

                </p>
                <p className="text-xs">Your order has been delivered</p>
                </div>}
                {false && <p>
                    <span>Expected delivery on March 3</span>
                </p>}
            </Grid>
        </Grid>
    </div>)
}