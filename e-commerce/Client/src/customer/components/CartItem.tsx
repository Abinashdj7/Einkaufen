import { AddCircleOutline, RemoveCircleOutline } from "@mui/icons-material"
import { Button, IconButton } from "@mui/material"
import { useDispatch } from "react-redux"
import { changeQuantity, removeItemFromCart, updateCartItem } from "./Action"

interface Props {
    item: any
}
export const CartItem = ({ item }: Props) => {
    const dispatch = useDispatch()
    const handleUpdateCartItem = (num) => {
        const data = {
            data:{quantity:item.quantity+num},
            cartItemId: item?.id
        }
        dispatch(updateCartItem(data))
        window.location.reload()
    }
    const handleRemoveCartItem = () => {
        console.log(item.id)
        dispatch(removeItemFromCart(item.id))
    }
    return (<div className="p-5 shadow-lg border rounded-md">
        <div className="flex items-center">
            <div className="w-[5rem] h-[5rem] lg:w-[9rem] lg:h-[9rem]">
                <img className="w-full h-full object-cover object-top" src={item.product.imageUrl} alt="" />
            </div>
            <div className="ml-5 space-y-1">
                <p className="font-semibold">{item.title}</p>
                <p className="opacity-70">Size: {item.sizes} ,White</p>
                <p className="opacity-70 mt-2">Seller: {item.brand}</p>
                <div className='flex space-x-5 items-center text-gray-900 pt-6'>
                    <p className='font-semibold'>${item.price}</p>
                    <p className='opacity-60 linethrough'>${item.discountedPrice}</p>
                    <p className='text-green-600 font-semibold'>{item.discountPercent}%off</p>
                </div>
            </div>
            <div className="lg:flex items-center lg:space-x-10 pt-4">
                <div className="flex items-center space-x-2">
                    <IconButton onClick={() => handleUpdateCartItem(-1)} disabled={item.quantity <= 1}>
                        <RemoveCircleOutline />
                    </IconButton>
                    <span className="py-1 border px-7 rounded-sm">{item.quantity}</span>
                    <IconButton onClick={() => handleUpdateCartItem(1)} sx={{ color: "RGB(145 85 253)" }}>
                        <AddCircleOutline />
                    </IconButton>
                </div>
                <div>
                    <Button sx={{ color: "RGB(145 85 253)" }} onClick={handleRemoveCartItem}>Remove</Button>
                </div>
            </div>
        </div>
    </div>)
}