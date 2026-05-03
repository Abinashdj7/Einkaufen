import { Button, Divider } from "@mui/material"
import { CartItem } from "./CartItem"
import { useNavigate } from "react-router-dom"
import { useDispatch, useSelector } from "react-redux"
import { useEffect, useState } from "react"
import { getCart } from "./Action"
import { store } from "./Store"

export const Cart = () => {
    const navigate=useNavigate()
    const dispatch=useDispatch()
    const {cart} =useSelector(store => store)
    const jwt=localStorage.getItem("jwt")
    const handleCheckOut=() => {
        navigate( "/checkout?step=2")
    }
    useEffect(() => {
        dispatch(getCart())
    },[cart.updateCartItem,cart.deleteCartItem])
    return (<div>
        <div className="lg:grid grid-cols-3 lg:px-16 relative">
            <div className="col-span-2">
                {jwt?cart.cart?.cartItems?.map((item) => <CartItem item={item}/>):null}
            </div>
            <div className="px-5 sticky top-0 h-[100vh] mt-5 lg:mt-0">
                <div className="border p-5 bg-white shadow-lg rounded-md">
                    <p className="font-bold opacity-60 pb-4">Price details</p>
                    <hr/>
                    <div className="space-y-3 font-semibold mb-10">
                        <div className="flex justify-between pt-3 text-black">
                            <span>Price</span>
                            <span>{cart.cart?.totalPrice}</span>
                        </div>
                        <div className="flex justify-between pt-3 text-black">
                            <span>Discount</span>
                            <span className="text-green-600">{cart.cart?.discountPercent}</span>
                        </div>
                        <div className="flex justify-between pt-3 text-black">
                            <span>Delivery charge</span>
                            <span className="text-green-600">Free</span>
                        </div>
                        <div className="flex justify-between pt-3 text-black">
                            <span>Total amount</span>
                            <span className="text-green-600 font-bold">{cart.cart?.totalDiscountedPrice}</span>
                        </div>
                    </div>
                    <Button onClick={handleCheckOut} variant="contained" className="w-full mt-5" sx={{px:"2.5rem",py:"0.7rem",bgcolor:"#9155fd"}}>Check out</Button>
                </div>
            </div>
        </div>
    </div>)
}