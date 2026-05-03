import AliceCarousel from "react-alice-carousel"
import { HomeSectionCard } from "./HomeSectionCard"
import { Button } from "@mui/material"
import { KeyboardArrowLeft } from "@mui/icons-material"
import { useState } from "react"
interface  Props {
    data: any[],
    sectionName:string;
    }
export const HomeSectionCarousel=({data,sectionName}:Props) => {
    const[activeIndex,setActiveIndex]=useState(0)
    const responsive={
        0:{items:1},
        720:{items:3},
        1024:{items:5.5}
    }
    const slidePrev=() => setActiveIndex(activeIndex - 1)
    const slideNext=() => setActiveIndex(activeIndex+1)

    const syncActiveIndex=({item}) => setActiveIndex(item)
    const items=data.slice(0,10).map((item) => <HomeSectionCard product={item}/>)
    return(
    
    <div className="px-4 lg:px-8 border py-5">
        <h2 className="text-2xl font-extrabold text-gray-800" >{sectionName}</h2>
        <div className="relative p-5">
    <AliceCarousel
    items={items}
    disableButtonsControls
    responsive={responsive}
    disableDotsControls
    onSlideChanged={syncActiveIndex}
    activeIndex={activeIndex}
    />
    {activeIndex!==items.length-1 &&
    <Button className="z-50 bg-white" variant="contained" sx={{position:'absolute',top:'8rem',right:'0rem',transform:'translateX(50%) rotate(90deg)'}} 
    aria-label="next" onClick={slideNext}>
        <KeyboardArrowLeft sx={{transform:'rotate(90deg)',color:'black'}}/>
    </Button>}
    {activeIndex!==0 && <Button className="z-50 bg-white" variant="contained" sx={{position:'absolute',top:'8rem',left:'0rem',transform:'translateX(50%) rotate(90deg)'}} 
    aria-label="next" onClick={slidePrev}>
        <KeyboardArrowLeft sx={{transform:'rotate(-90deg)',color:'black'}}/>
    </Button>}
    </div>
    </div>)
}