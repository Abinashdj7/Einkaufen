import { Grid } from "@mui/material"
import { Achievement } from "./Achievement"
import { MonthlyOverview } from "./MonthlyOverview"
import { ProductTable } from "./ProductTable"
import NewsLetterSubscription from "./NewLetterSubscription"


export const AdminDashBoard=() => {
    return(<div>
    <Grid container spacing={2}>
            <NewsLetterSubscription/>
    </Grid>
    </div>)
}
