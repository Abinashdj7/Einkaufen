import { useNavigate } from "react-router-dom"

interface Props {
    product: any;
}

export const ProductCard = ({ product }: Props) => {
    const navigate = useNavigate()

    return (
        <div
            onClick={() => navigate(`/product/${product.id}`)}
            className="group relative bg-gradient-to-br from-slate-50/50 to-white/50 backdrop-blur-sm border border-white/30 rounded-2xl shadow-xl hover:shadow-2xl hover:-translate-y-2 product-hover w-full max-w-sm mx-auto transition-all duration-500 cursor-pointer overflow-hidden"
        >
            {/* Product Image */}
            <div className="relative h-64 overflow-hidden bg-gradient-to-br from-primary-50 to-secondary-50">
                <img
                    className="w-full h-full object-cover object-center group-hover:scale-110 transition-transform duration-500"
                    src={product.imageUrl}
                    alt={product.title}
                />
                {/* Discount Badge */}
                {product.discountPercent > 0 && (
                    <div className="absolute top-4 left-4 bg-gradient-to-r from-primary-600 to-primary-700 text-white px-3 py-1 rounded-full text-xs font-bold shadow-lg">
                        -{product.discountPercent}%
                    </div>
                )}
            </div>

            {/* Product Info */}
            <div className="p-6">
                <div className="space-y-2">
                    <p className="text-sm font-semibold text-gray-600 uppercase tracking-wide gradient-text/70">
                        {product.brand}
                    </p>
                    <h3 className="text-lg font-bold text-gray-900 line-clamp-2 leading-tight">
                        {product.title}
                    </h3>
                </div>

                <div className="flex items-center justify-between mt-4 pt-4 border-t border-gray-100">
                    <div className="flex items-center space-x-3">
                        <span className="text-2xl font-bold text-primary-700">
                            ₹{product.discountedPrice}
                        </span>
                        <span className="text-lg text-gray-400 line-through">
                            ₹{product.price}
                        </span>
                    </div>
                    <div className="flex items-center space-x-1">
                        <span className="text-yellow-400">★</span>
                        <span className="text-sm text-gray-600 font-medium">4.5</span>
                    </div>
                </div>

                {/* Add to Cart Button */}
                <button className="btn-primary w-full mt-4 group-hover:bg-primary-800">
                    Add to Cart
                </button>
            </div>
        </div>
    )
}
