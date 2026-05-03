import Button from "@mui/material/Button"
import { AddressCard } from "./AddressCard"
import { CartItem } from "./CartItem"
import { useDispatch, useSelector } from "react-redux"
import { useEffect } from "react"
import { createPayment, getOrderById } from "./Action"
import { store } from "./Store"
import { useNavigate } from "react-router-dom"

export const OrderSummary=() => {
    const dispatch=useDispatch()
    const navigate=useNavigate()
    const searchParams=new URLSearchParams(window.location.search)
    const orderId=searchParams.get("order_id")
    const {order} =useSelector(store => store)
    const handleCheckOut=() => {
        dispatch(createPayment(orderId))
        navigate("/checkout?step=4")
    }
    useEffect(() => {
        dispatch(getOrderById(orderId))
    },[orderId])
    return(<>
        <div className="p-5 shadow-lg rounded-s-md border">
            <AddressCard address={order.order?.shippingAddress}/>
        </div>
        <div>
        <div className="lg:grid grid-cols-3 relative">
            <div className="col-span-2">
                {order.order?.orderItems.map((item) => <CartItem item={item}/>)}
            </div>
            <div className="px-5 sticky top-0 h-[100vh] mt-5 lg:mt-0">
                <div className="border">
                    <p className="uppercase font-bold opacity-60 pb-4">Price details</p>
                    <hr/>
                    <div className="space-y-3 font-semibold mb-10">
                        <div className="flex justify-between pt-3 text-black">
                            <span>Price</span>
                            <span>${order.order?.totalPrice}</span>
                        </div>
                        <div className="flex justify-between pt-3 text-black">
                            <span>Discount</span>
                            <span className="text-green-600">${order.order?.discountedPrice}</span>
                        </div>
                        <div className="flex justify-between pt-3 text-black">
                            <span>Delivery charge</span>
                            <span className="text-green-600">$24</span>
                        </div>
                        <div className="flex justify-between pt-3 text-black">
                            <span>Total amount</span>
                            <span className="text-green-600 font-bold">${order.order?.totalDiscountedPrice}</span>
                        </div>
                    </div>
                    <Button variant="contained" className="w-full mt-5" sx={{px:"2.5rem",py:"0.7rem",bgcolor:"#9155fd"}}
                    onClick={handleCheckOut}
                    >Check out</Button>
                </div>
            </div>
        </div>
    </div>
    </>)
}