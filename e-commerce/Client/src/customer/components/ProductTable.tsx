import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { deleteProduct, findProducts } from './Action';
import { useSearchParams } from 'react-router-dom';
import { store } from './Store';
import { Avatar, Button, Card, CardHeader } from '@mui/material';


export const ProductTable =() => {
    const dispatch=useDispatch()
    const {product} =useSelector(store => store)
    const handleProductDelete=(productId) => {
      dispatch(deleteProduct(productId))
    }
    useEffect(() => {
        const data={
            category:'mens_jacket',
            colors:[],
            sizes:[],
            minPrice:null,
            maxPrice:null,
            minDiscount:0,
            sort:"price_low",
            pageNumber:0,
            pageSize:10,
            stock:""
        }
        findProducts(data)
    },[product.deletedProduct])
  return (
    <div className='p-5'>
      <Card className='mt-2 bg-[#1B1B1B]'>
          <CardHeader title="All products" />
      
      <TableContainer component={Paper} sx={{bgcolor:"#242B2E"}}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Dessert (100g serving)</TableCell>
            <TableCell align="right">Title</TableCell>
            <TableCell align="right">Category</TableCell>
            <TableCell align="right">Price</TableCell>
            <TableCell align="right">Quantity</TableCell>
            <TableCell align="right">Delete</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {product.products.map((prod) => (
            <TableRow
              key={prod.name}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell align="left">
                <Avatar src={prod.imageUrl}></Avatar>
              </TableCell>
              <TableCell component="th" scope="row">
                {prod.name}
              </TableCell>
              <TableCell align="left">{prod.category.name}</TableCell>
              <TableCell align="left">{prod.price}</TableCell>
              <TableCell align="left">{prod.quantity}</TableCell>
              <TableCell align="left">
              <Button onClick={() => handleProductDelete(prod.id)} variant='outlined'>Delete</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </Card>
    </div>
  );
}