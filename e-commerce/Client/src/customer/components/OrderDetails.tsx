import { Box, Grid } from "@mui/material"
import { AddressCard } from "./AddressCard"
import { OrderTracker } from "./OrderTracker"
import { deepPurple } from "@mui/material/colors"
import { Star, StarBorder } from "@mui/icons-material"

export const OrderDetails=() => {
    return(<div className="px-5 lg:px-20">
        <div>
            <h1 className="font-bold text-xl py-10">Delivery address</h1>
            <AddressCard/>
        </div>
        <div className="py-20">
            <OrderTracker activeStep={3}/>
        </div>
        <Grid className="space-y-5" container>
            {[1,1,1,1,1].map((item) => (<Grid item container className="shadow-xl rounded-md p-5 border" sx={{alignItems:"center",justifyContent:"space-between"}}>
                <Grid item xs={6}>
                    <div className="flex items-center space-x-2">
                        <img className="w-[5rem] h-[5rem] object-cover object-top" src="https://i.pinimg.com/564x/8a/ac/e1/8aace1270554450a9cdee0feb226c5c3.jpg"/>
                        <div className="space-y-2 ml-5">
                            <p className="font-semibold">Black churidar</p>
                            <p className="space-x-5 opacity-50 text-sm font-semibold"><span>Colour : Black</span><span>Size : L</span></p>

                        </div>
                    </div>
                </Grid>
                <Grid item>
                    <Box sx={{color:deepPurple[500]}}>
                        <StarBorder sx={{fontSize:"2rem"}} className="px-2"/>
                        <span>Rate and review product</span>
                    </Box>
                </Grid>
            </Grid>))}
        </Grid>
    </div>)
}