import { useState } from "react";
import { useAppDispatch } from "../store/types";
import { basicSearch, geoSearch } from "../store/actions/contract-actions";
import { SearchResults } from "../components/SearchResults";

export const BasicSearch: React.FunctionComponent = () => {
    const [query, setQuery] = useState<string>('');
    const [city, setCity] = useState<string>('');
    const [radius, setRadius] = useState<number>(0);
    const dispatch = useAppDispatch();
    const search = async () => {
        await dispatch(basicSearch(query));
    };

    const geolocationSearch = async () => {
        await dispatch(geoSearch({city, radius}));
    };

    return (
        <div className="w-full flex h-full">
            <div className="border-e border-light-gray h-full pe-8 w-[40%]">
                <span className="font-extralight mb-12 block">
                    <strong>Search by </strong> 
                    employee name, government entity and administrative level, 
                    and perform <strong>full-text </strong> search for both contract and law content.
                </span>
                <div className="flex gap-10">
                    <input type="text" className="w-2/3" placeholder="Enter query" value={query} onChange={e => setQuery(e.target.value)}/>
                    <button className="primary-button" onClick={() => search()}>Search</button>
                </div>

                <span className="font-extralight mt-32 mb-12 block">
                    <strong>Search government </strong> by city and radius
                </span>
                <div className="flex gap-10">
                    <input type="text" className="w-1/2" placeholder="Enter city" value={city} onChange={e => setCity(e.target.value)}/>
                    <input type="number"  className="w-1/2" placeholder="Enter radius" value={radius} onChange={e => setRadius(+e.target.value)}/>
                    <button className="primary-button" onClick={() => geolocationSearch()}>Search</button>
                </div>
            </div>
            <div className="w-[60%] px-5">
                <SearchResults/>
            </div>
        </div>
    )
};