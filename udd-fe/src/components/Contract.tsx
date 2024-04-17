import { useState, useEffect } from "react";
import { setContract } from "../store/slices/contract-slice";
import { useAppDispatch, useAppSelector } from "../store/types";
import { Contract, ContractName } from "../types/document";
import { indexContract } from "../store/actions/contract-actions";

interface ContractCompProps {
    editable: boolean;
}

export const ContractComp: React.FunctionComponent<ContractCompProps> = ({editable}) => {
    const submittedContract: Contract | null = useAppSelector((state) => state.contractSlice.contract);
    const [changedContract, setChangedContract] = useState<Contract | null>(null);
    const dispatch= useAppDispatch();

    const changeValue = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: string) => {
        const value = e.target.value;
        if (!changedContract) return;
        setChangedContract({...changedContract, [key]: value});
    };

    const confrim = async () => {
        if (!changedContract) return;
        dispatch(setContract(changedContract));
        await dispatch(indexContract(changedContract));
    }

    const discard = () => {
        if (!submittedContract) return;
        setChangedContract(submittedContract);
    }

    useEffect(() => {
        if (!submittedContract) return;
        setChangedContract(submittedContract);
    }, [submittedContract])
    
    if (!changedContract) return <></>;
    return (
        <div className="flex flex-col gap-10 mt-20 overflow-auto">
            <div className="flex flex-col gap-8 m-10">
            {
                Object.keys(changedContract).map((key, index) => {
                    if (key === 'id') return <></>;
                return (
                    <span className="flex items-end gap-10" key={index}>
                        <label className="whitespace-nowrap w-1/4">{ContractName[key as keyof typeof ContractName]}</label>
                        {key !== 'content' && <input className="w-3/4" type="text" value={changedContract[key as keyof Contract]} disabled={!editable} onChange={e => changeValue(e, key)}/>}
                        {key === 'content' && <textarea className="w-3/4" value={changedContract[key as keyof Contract]} disabled={!editable} onChange={e => changeValue(e, key)} />}
                    </span>
                );
                })      
            }
            </div>
            <div className="me-10 flex justify-end gap-10">
            <button className="secondary-button" onClick={e => discard()}>Discard changes</button>
            <button className="primary-button" onClick={e => confrim()}>Confirm</button>
            </div>
        </div>
    )
};