export const navigation = {
    categories: [
        {
            id: 'electronics',
            name: 'Electronics',
            featured: [
                {
                    name: 'Smartphones',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500&fit=crop',
                    imageAlt: 'Latest smartphones collection',
                },
                {
                    name: 'Laptops',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=500&fit=crop',
                    imageAlt: 'High performance laptops',
                },
            ],
            sections: [
                {
                    id: 'devices',
                    name: 'Devices',
                    items: [
                        { name: 'Smartphones', id: "smartphones" },
                        { name: 'Laptops', id: "laptops" },
                        { name: 'Tablets', id: "tablets" },
                        { name: 'Audio', id: "audio" },
                        { name: 'Wearables', id: "wearables" },
                    ],
                },
                {
                    id: 'accessories',
                    name: 'Accessories',
                    items: [
                        { name: 'Headphones', id: "headphones" },
                        { name: 'Chargers', id: "chargers" },
                        { name: 'Cases', id: "cases" },
                        { name: 'Cables', id: "cables" },
                    ],
                },
            ],
        },
        {
            id: 'fashion',
            name: 'Fashion',
            featured: [
                {
                    name: 'New Arrivals',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1445205170230-053b83016050?w=500&fit=crop',
                    imageAlt: 'Latest fashion trends',
                },
                {
                    name: 'Best Sellers',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1531349960316-210eb04c27bc?w=500&fit=crop',
                    imageAlt: 'Top selling fashion items',
                },
            ],
            sections: [
                {
                    id: 'men',
                    name: "Men's",
                    items: [
                        { name: 'Shirts', id: 'mens_shirt' },
                        { name: 'Jeans', id: 'mens_jeans' },
                        { name: 'T-Shirts', id: 'mens_t-shirt' },
                        { name: 'Jackets', id: 'mens_jacket' },
                        { name: 'Shoes', id: 'mens_shoes' },
                    ],
                },
                {
                    id: 'women',
                    name: "Women's",
                    items: [
                        { name: 'Dresses', id: 'womens_dress' },
                        { name: 'Tops', id: 'womens_top' },
                        { name: 'Jeans', id: 'womens_jeans' },
                        { name: 'Sarees', id: 'womens_saree' },
                        { name: 'Kurtas', id: 'womens_kurtas' },
                    ],
                },
            ],
        },
        {
            id: 'home',
            name: 'Home & Living',
            featured: [
                {
                    name: 'Furniture',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=500&fit=crop',
                    imageAlt: 'Modern furniture collection',
                },
                {
                    name: 'Decor',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=500&fit=crop',
                    imageAlt: 'Home decor essentials',
                },
            ],
            sections: [
                {
                    id: 'furniture',
                    name: 'Furniture',
                    items: [
                        { name: 'Sofas', id: "sofas" },
                        { name: 'Beds', id: "beds" },
                        { name: 'Tables', id: "tables" },
                        { name: 'Chairs', id: "chairs" },
                    ],
                },
                {
                    id: 'decor',
                    name: 'Decor & Appliances',
                    items: [
                        { name: 'Lamps', id: "lamps" },
                        { name: 'Kitchen', id: "kitchen" },
                        { name: 'Bedding', id: "bedding" },
                        { name: 'Appliances', id: "appliances" },
                    ],
                },
            ],
        },
        {
            id: 'books',
            name: 'Books',
            featured: [
                {
                    name: 'Tech Books',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=500&fit=crop',
                    imageAlt: 'Programming books',
                },
                {
                    name: 'Bestsellers',
                    href: '/',
                    imageSrc: 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=500&fit=crop',
                    imageAlt: 'Popular fiction titles',
                },
            ],
            sections: [
                {
                    id: 'categories',
                    name: 'Categories',
                    items: [
                        { name: 'Programming', id: "programming" },
                        { name: 'Fiction', id: "fiction" },
                        { name: 'Self Help', id: "self-help" },
                        { name: 'Business', id: "business" },
                        { name: 'Biography', id: "biography" },
                    ],
                },
            ],
        },
    ],
    pages: [
        { name: 'Deals', href: '/deals' },
        { name: 'About', href: '/about' },
        { name: 'Contact', href: '/contact' },
    ],
}
