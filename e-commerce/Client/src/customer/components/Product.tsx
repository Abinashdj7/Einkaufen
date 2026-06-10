import { Fragment, useState } from "react";
import { Dialog, Disclosure, Menu, Transition } from "@headlessui/react";
import { XMarkIcon } from "@heroicons/react/24/outline";
import {
  ChevronDownIcon,
  FunnelIcon,
  MinusIcon,
  PlusIcon,
  Squares2X2Icon,
} from "@heroicons/react/20/solid";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import Pagination from "@mui/material/Pagination";

import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { Backdrop, CircularProgress } from "@mui/material";
import { findProducts, getAllProducts, getProductsByCategory } from "./Action";
import { ProductCard } from "./ProductCard";
import { filters, singleFilter, sortOptions } from "./ProductFilter";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export default function Product() {
  const [mobileFiltersOpen, setMobileFiltersOpen] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const param = useParams();
  const productState = useSelector((state: any) => state.product);
  const location = useLocation();

  const [isLoaderOpen, setIsLoaderOpen] = useState(false);

  const handleLoderClose = () => {
    setIsLoaderOpen(false);
  };
  const decodedQueryString = decodeURIComponent(location.search);
  const searchParams = new URLSearchParams(decodedQueryString);
  const colorValue = searchParams.get("color");
  const sizeValue = searchParams.get("size");
  const price = searchParams.get("price");
  const discount = searchParams.get("discount");
  const sortValue = searchParams.get("sort");
  const pageNumber = searchParams.get("page") || 1;
  const stock = searchParams.get("stock");

  const handleSortChange = (value) => {
    const searchParams = new URLSearchParams(location.search);
    searchParams.set("sort", value);
    const query = searchParams.toString();
    navigate({ search: `?${query}` });
  };
  const handlePaginationChange = (event, value) => {
    const searchParams = new URLSearchParams(location.search);
    searchParams.set("page", value);
    const query = searchParams.toString();
    navigate({ search: `?${query}` });
  };

  useEffect(() => {
    const [minPrice, maxPrice] =
      price === null ? [0, 0] : price.split("-").map(Number);
    const data = {
      firstLevel: param.levelOne,
      secondLevel: param.levelTwo,
      thirdLevel: param.levelThree,
    };
    dispatch(getProductsByCategory(data));
  }, [dispatch, param.levelOne, param.levelTwo, param.levelThree, location.search]);

  const handleFilter = (value, sectionId) => {
    const searchParams = new URLSearchParams(location.search);

    let filterValues = searchParams.getAll(sectionId);

    if (filterValues.length > 0 && filterValues[0].split(",").includes(value)) {
      filterValues = filterValues[0]
        .split(",")
        .filter((item) => item !== value);
      if (filterValues.length === 0) {
        searchParams.delete(sectionId);
      }
    } else {
      filterValues.push(value);
    }

    if (filterValues.length > 0)
      searchParams.set(sectionId, filterValues.join(","));

    const query = searchParams.toString();
    navigate({ search: `?${query}` });
  };

  const handleRadioFilterChange = (e, sectionId) => {
    const searchParams = new URLSearchParams(location.search);
    searchParams.set(sectionId, e.target.value);
    const query = searchParams.toString();
    navigate({ search: `?${query}` });
  };

  useEffect(() => {
    if (productState?.loading) {
      setIsLoaderOpen(true);
    } else {
      setIsLoaderOpen(false);
    }
  }, [productState?.loading]);

  return (
    <div className="bg-white -z-20 ">
      <div>
        <Transition.Root show={mobileFiltersOpen} as={Fragment}>
          <Dialog
            as="div"
            className="relative z-40 lg:hidden"
            onClose={setMobileFiltersOpen}
          >
            <Transition.Child
              as={Fragment}
              enter="transition-opacity ease-linear duration-300"
              enterFrom="opacity-0"
              enterTo="opacity-100"
              leave="transition-opacity ease-linear duration-300"
              leaveFrom="opacity-100"
              leaveTo="opacity-0"
            >
              <div className="fixed inset-0 bg-black bg-opacity-25" />
            </Transition.Child>

            <div className="fixed inset-0 z-40 flex">
              <Transition.Child
                as={Fragment}
                enter="transition ease-in-out duration-300 transform"
                enterFrom="translate-x-full"
                enterTo="translate-x-0"
                leave="transition ease-in-out duration-300 transform"
                leaveFrom="translate-x-0"
                leaveTo="translate-x-full"
              >
                <Dialog.Panel className="relative ml-auto flex h-full w-full max-w-xs flex-col overflow-y-auto bg-white py-4 pb-12 shadow-xl">
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </Dialog>
        </Transition.Root>

        <main className="mx-auto px-4 lg:px-14 ">
          <div className="flex items-baseline justify-between border-b border-gray-200 pb-6">
            <h1 className="text-4xl font-bold tracking-tight text-gray-900">
              Product
            </h1>

            <div className="flex items-center">
              <Menu as="div" className="relative inline-block text-left">
                <div>
                </div>
              </Menu>
            </div>
          </div>

          <section aria-labelledby="products-heading" className="pb-24 pt-6">
            <h2 id="products-heading" className="sr-only">
              Products
            </h2>

            <div>
              <div className="grid grid-cols-1 gap-x-8 gap-y-10 lg:grid-cols-5">
                <div className="lg:col-span-4 w-full ">
                  <div className="flex flex-wrap justify-center bg-white border py-5 rounded-md ">
                    {productState?.products?.map((item, index) => (
                      <ProductCard key={item?.id ?? index} product={item} />
                    ))}
                    {productState?.products?.length === 0 && !productState?.loading && (
                      <div className="w-full text-center py-12 text-gray-600">
                        No products match this category or filter.
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>

        <section className="w-full px-[3.6rem]">
          <div className="mx-auto px-4 py-5 flex justify-center shadow-lg border rounded-md">
            <Pagination
              count={Math.max(1, Math.ceil((productState?.products?.length || 0) / 10))}
              color="primary"
              className=""
              onChange={handlePaginationChange}
            />
          </div>
        </section>

        <section>
          <Backdrop
            sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
            open={isLoaderOpen}
            onClick={handleLoderClose}
          >
            <CircularProgress color="inherit" />
          </Backdrop>
        </section>
      </div>
    </div>
  );
}
