import { useState } from "react";
import { useAppSelector } from "../store/types";
import { MdImageSearch } from "react-icons/md";
import { VscEyeClosed } from "react-icons/vsc";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import { Contract, ContractName } from "../types/document";
import { GoDownload } from "react-icons/go";


interface HighlightProps {
    highlights: Record<keyof Contract, string>;
}

const Highlights: React.FunctionComponent<HighlightProps> = ({highlights}) => {

    const displayHighlight = (highlight: string, key: string) => {
        if (key.toLowerCase().includes('content')) return `...${highlight.replaceAll('em>', 'b>')}...`;
        return `${highlight.replaceAll('em>', 'b>')}`
    }

    return (
        <div className="flex flex-col gap-5">
            <span className="font-bold">Result found in</span>
            {
                Object.keys(highlights).map((key, ind) => (
                    <span key={ind} className="flex items-center gap-5">
                        <span className="font-light">{ContractName[key as keyof typeof ContractName] ?? 'Contract content:'}</span>
                        <div className="font-light" dangerouslySetInnerHTML={{ __html: displayHighlight(highlights[key as keyof Contract], key) }} />
                    </span>
                ))
            }
        </div>
    )
}

export const SearchResults: React.FunctionComponent = () => {
    const { searchResults } = useAppSelector(state => state.contractSlice);
    const [index, setIndex ] = useState<number>(0);

    const pageDown = () => {
        if (index === 0) return;
        setIndex(index - 1);
    }

    const pageUp = () => {
        if (!searchResults) return;
        if (index === searchResults.length - 1) return;
        setIndex(index + 1);
    }

    const downloadFile = async (filename: string) => {
        const res = await fetch(`http://localhost:8080/api/file/${filename}`);
        const blob = await res.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        a.click();
    }

    if (!searchResults) return (
    <div className="flex flex-col w-full items-center justify-center relative h-1/3">
        <span className="z-50 absolute top-1/2 text-6xl">Search for results</span>
        <div className="z-10 absolute -top-20">
        <MdImageSearch size={550} color="#E0E0E0"/>
        </div>
    </div>
    )
    if (searchResults.length < 1) return (
        <div className="flex flex-col w-full items-center justify-center relative h-1/3">
            <span className="z-50 absolute top-1/2 text-6xl">No results found</span>
            <div className="z-10 absolute -top-20">
            <VscEyeClosed size={550} color="#E0E0E0"/>
            </div>
        </div>
        )
    return <div className="overflow-auto px-5">
        {searchResults[index].highlights && Object.keys(searchResults[index].highlights!).length > 0 && <Highlights highlights={searchResults[index].highlights!}/> }
        <div className="w-full items-center justify-between flex my-10">
        <span className="font-bold">Whole document:</span>
        <button className="secondary-button" onClick={e => downloadFile(searchResults[index].serverFilename!)}>
            <GoDownload size={20} color="#ffffff"/>
            Download
        </button>
        </div>
        {
            Object.keys(searchResults[index]).map((key, ind) => {
                if (key === 'id' || key === 'serverFilename' || key === 'highlights' || !searchResults[index][key as keyof typeof ContractName]) return <></>;
                return (
                    <span className="flex items-center gap-10 mb-2" key={ind}>
                        <label className="whitespace-nowrap w-1/4 font-light">{ContractName[key as keyof typeof ContractName]}:</label>
                        <p className="w-3/4 text-justify font-light"> {searchResults[index][key as keyof typeof ContractName]} </p>
                    </span>
                );
            })
        }
        <div className="flex w-full justify-end gap-5 items-center">
            <button onClick={() => pageDown()}><IoIosArrowDropleft size={30} color={index === 0 ? "#D3D3D3" : "#d87860"}/></button>
            <p className="font-light">{index +1} of {searchResults.length}</p>
            <button onClick={() => pageUp()}><IoIosArrowDropright size={30} color={index === searchResults.length - 1 ? "#D3D3D3" : "#d87860"}/></button>
        </div>
    </div>
}