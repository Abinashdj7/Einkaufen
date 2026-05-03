import { Step, StepLabel, Stepper } from "@mui/material"

interface Props{
    activeStep:any
} 
const steps=["Place","Order confirmed","Shipped","Out for delivery","Delivered"]
export const OrderTracker=({activeStep}:Props) => {
    return(<div className="w-full">
        <Stepper activeStep={activeStep} alternativeLabel>
            {steps.map((label) => (<Step>
                <StepLabel sx={{color:'#9155FD',fontSize:"44px"}}>{label}</StepLabel>
            </Step>))}
        </Stepper>
    </div>)
}