import { Grid } from "@mui/material"
import { Achievement } from "./Achievement"
import { MonthlyOverview } from "./MonthlyOverview"
import { ProductTable } from "./ProductTable"
import NewsLetterSubscription from "./NewLetterSubscription"


export const AdminDashBoard=() => {
    return(<div>
    <Grid container spacing={2}>
            <NewsLetterSubscription/>
        {/*
        <Grid item xs={12} md={8}>
            <MonthlyOverview/>
        </Grid>
        <Grid item md={6}>
            <ProductTable/>
        </Grid> */}
    </Grid>
    </div>)
}
