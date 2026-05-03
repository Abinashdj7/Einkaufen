import AliceCarousel from 'react-alice-carousel';
import 'react-alice-carousel/lib/alice-carousel.css';
import { MainCarouselData } from './MainCarouselData';
import { useNavigate } from 'react-router-dom';

const items = MainCarouselData.map((item) => <img className='cursor-pointer -z-10' style={{width:"100%",height:"auto"}} role='presentation' src={item.image}></img>)

const MainCarousel = () => (
    <AliceCarousel
        items={items}
        disableButtonsControls
        autoPlay
        autoPlayInterval={1000}
        infinite
    />
);

export default MainCarousel