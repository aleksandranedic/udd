import { useState } from "react";
import { useAppDispatch } from "../store/types";
import { basicSearch } from "../store/actions/contract-actions";

export const BasicSearch: React.FunctionComponent = () => {
    const [query, setQuery] = useState<string>('');
    const dispatch = useAppDispatch();
    const search = async () => {
        console.log('Searching for ' + query);
        await dispatch(basicSearch(query));
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
                    <strong>Search government </strong> by geolocation
                </span>
                <div className="flex gap-10">
                    <input type="text" className="w-1/2" placeholder="Enter city" />
                    <input type="number"  className="w-1/2" placeholder="Enter radius" />
                    <button className="primary-button">Search</button>
                </div>
            </div>
            <div className="w-[60%] px-5">

            </div>
        </div>
    )
};