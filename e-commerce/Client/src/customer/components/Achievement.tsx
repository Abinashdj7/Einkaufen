import { Button, Card, CardContent, Typography } from "@mui/material"
import { styled } from "@mui/system"

const Image=styled("img")({
    right:0,
    bottom:0,
    height:170,
    position:"absolute"
})

const TrophyImage=styled("img")({
    right:36,
    bottom:20,
    height:98,
    position:"absolute"
})

export const Achievement=() => {
    return(<>
    <Card className="space-y-5" sx={{position:"relative",bgcolor:"black",color:""}}>
        <CardContent>
            <Typography variant="h6"
            sx={{letterSpacing:".25px"}}
            >Shop with Abinash</Typography>
            <Typography variant="body2">Congrulations</Typography>
            <Typography variant="h5">420.8k</Typography>
            <Button size="small" variant="contained" sx={{my:3.1}}>View sales</Button>
            <Image src=""/>
            <TrophyImage src="https://img.freepik.com/premium-vector/shiny-golden-trophy-with-star-illustration-cartoon-icon_400474-507.jpg"/>
        </CardContent>
    </Card>
    </>)
}