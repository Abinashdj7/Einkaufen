interface Props{
    address:any;
}
export const AddressCard=({address}:Props) => {
    return(<>
    <div className="space-y-3">
        <p className="font-semi-bold">{address && address.firstName+" "+address.lastName}</p>
        <p>{address && address.street},{address && address.state},{address && address.zpiCode}</p>
        <div className="space-y-1">
            <p>Phone number</p>
            <p>{address && address.mobile}</p>
        </div>

    </div>
    </>)
}