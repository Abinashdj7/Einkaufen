import { CurrencyEuroIcon } from "@heroicons/react/20/solid"
import { AccountBoxOutlined, AttachMoney, CellTowerRounded, MoreVert, TrendingUp } from "@mui/icons-material"
import { Avatar, Box, Card, CardContent, CardHeader, Grid, IconButton, Typography } from "@mui/material"

export const MonthlyOverview=() => {
    const salesData=[
        {
            stats:'245k',
            title:"Sales",
            color:"primary",
            icon:<TrendingUp sx={{fontSize:"1.75rem"}}/>
        },
        {
            stats:'12.5k',
            title:"Customer",
            color:"success",
            icon:<AccountBoxOutlined sx={{fontSize:"1.75rem"}}/>
        },
        {
            stats:'1.54k',
            title:"Products",
            color:"#06d6a0",
            icon:<CellTowerRounded sx={{fontSize:"1.75rem"}}/>
        },
        {
            stats:'88k',
            title:"Revenue",
            color:"info",
            icon:<AttachMoney sx={{fontSize:"1.75rem"}}/>
        },
    ]
    const renderState=() => {
        salesData.map((item,index) => (
            <Grid item xs={12} sm={3} key={index}>
                <Box sx={{
                    display:"flex",alignItems:"center"
                }}>
                    <Avatar variant="rounded" 
                    sx={{
                        mr:3,
                        width:44,
                        height:44,
                        boxShadow:3,
                        color:"white",
                        background:`${item.color}`
                    }}
                    >{item.icon}</Avatar>
                    <Box
                    sx={{display:"flex",flexDirection:"column"}}
                    >
                        <Typography variant="caption">{item.title}</Typography>
                        <Typography></Typography>
                    </Box>
                </Box>
            </Grid>
        ))
    }
    return(<Card sx={{backgroundColor:"#242B2E"}}>
    <CardHeader title="Monthly overview" action={
        <IconButton size="small">
            <MoreVert/>
        </IconButton>
    } subheader={
        <Typography variant="body2">
            <Box component="span" sx={{fontWeight:600,mx:2}}>
                Total 48.5 % growth
            </Box>
            this month
        </Typography>
    } 
    titleTypographyProps={{
        sx:{
            mb:2.5,
            lineHeight:"2rem !important",
            letterSpacing:".15px !important"
        }
    }}
    />
    <CardContent sx={{pt:theme => `${theme.spacing(3)} !important`}}>
        <Grid container spacing={[5,0]}>
            {renderState()}
        </Grid>
    </CardContent>
    
    </Card>)
}