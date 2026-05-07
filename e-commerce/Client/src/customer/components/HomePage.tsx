import { useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"
import { HomeSectionCarousel } from "./HomeSectionCarousel"
import MainCarousel from "./MainCarousel"
import { getAllProducts } from "./Action"

export const HomePage = () => {
    const dispatch = useDispatch()
    const { products, loading } = useSelector(state => state.product)

    useEffect(() => {
        dispatch(getAllProducts())
    }, [dispatch])

    if (loading && products.length === 0) {
        return (
            <div className="min-h-[60vh] flex items-center justify-center">
                <p className="text-lg font-medium text-gray-700">Loading products...</p>
            </div>
        )
    }

    if (!loading && products.length === 0) {
        return (
            <div className="min-h-[60vh] flex flex-col items-center justify-center text-center px-4">
                <h2 className="text-2xl font-bold text-gray-900">No products found</h2>
                <p className="text-gray-600 mt-2">Please check back later or add inventory through the admin panel.</p>
            </div>
        )
    }

    const getProductsByCategory = (categoryPrefix: string) => {
        return products.filter((p: any) =>
            p.category && p.category.name && p.category.name.startsWith(categoryPrefix)
        )
    }

    const electronics = getProductsByCategory("electronics")
    const mensClothing = getProductsByCategory("mens_")
    const womensClothing = getProductsByCategory("womens_")
    const homeLiving = getProductsByCategory("home")
    const books = getProductsByCategory("books")

    return (<><div>
        <MainCarousel />
    </div>
        <div className="space-y-10 py-20 flex flex-col justify-center px-5 lg:px-10">
            {electronics.length > 0 && <HomeSectionCarousel data={electronics.slice(0, 10)} sectionName={"Featured Electronics"} />}
            {mensClothing.length > 0 && <HomeSectionCarousel data={mensClothing.slice(0, 10)} sectionName={"Men's Fashion"} />}
            {womensClothing.length > 0 && <HomeSectionCarousel data={womensClothing.slice(0, 10)} sectionName={"Women's Fashion"} />}
            {homeLiving.length > 0 && <HomeSectionCarousel data={homeLiving.slice(0, 8)} sectionName={"Home & Living"} />}
            {books.length > 0 && <HomeSectionCarousel data={books.slice(0, 8)} sectionName={"Best Books"} />}

            {products.length > 0 && mensClothing.length === 0 && womensClothing.length === 0 && (
                <HomeSectionCarousel data={products} sectionName={"All Products"} />
            )}
        </div>
    </>
    )
}
