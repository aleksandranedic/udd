import { useState, useEffect } from "react";
import { resetContract, setContract } from "../store/slices/contract-slice";
import { useAppDispatch, useAppSelector } from "../store/types";
import { Contract, ContractName, DocumentType } from "../types/document";
import { indexContract, indexLaw } from "../store/actions/contract-actions";

interface ContractCompProps {
    editable: boolean;
    type: DocumentType;
}

interface DocumentDisplayProps extends ContractCompProps {
    contract: Contract;
    setChangedContract: (contract: Contract) => void;
}

const DocumentDisplay: React.FunctionComponent<DocumentDisplayProps> = ({editable, type, contract, setChangedContract}) => {
    const changeValue = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: string) => {
        const value = e.target.value;
        if (!contract) return;
        setChangedContract!({...contract, [key]: value});
    };

    if (type === DocumentType.Contract) {
        return (
            <>
            {
                Object.keys(contract).map((key, index) => {
                    if (key === 'id' || key === 'lawContent' || key === 'highlights' || key === 'serverFilename') return <></>;
                    return (
                        <span className="flex items-end gap-10" key={index}>
                        <label className="whitespace-nowrap w-1/4">{ContractName[key as keyof typeof ContractName]}</label>
                        {key !== 'content' && <input className="w-3/4" type="text" value={contract[key as keyof Contract] as string} disabled={!editable} onChange={e => changeValue(e, key)}/>}
                        {key === 'content'  && <textarea className="w-3/4" value={contract[key as keyof Contract] as string} disabled={!editable} onChange={e => changeValue(e, key)} />}
                    </span>
                );
            })      
        }
        </>
        );
    }
    return (
     <>
        <span className="flex items-end gap-10">
            <label className="whitespace-nowrap w-1/4">{ContractName['lawContent']}</label>
            <textarea className="w-3/4" rows={15} value={contract['lawContent']} disabled={!editable} onChange={e => changeValue(e, 'lawContent')} />
        </span>
     </>
    ); 
};

export const ContractComp: React.FunctionComponent<ContractCompProps> = ({editable, type}) => {
    const submittedContract: Contract | null = useAppSelector((state) => state.contractSlice.contract);
    const [changedContract, setChangedContract] = useState<Contract | null>(null);
    const dispatch= useAppDispatch();

    const confrim = async () => {
        if (!changedContract) return;
        dispatch(setContract(changedContract));
        type === DocumentType.Contract ?  await dispatch(indexContract(changedContract)) : await dispatch(indexLaw(changedContract));
        dispatch(resetContract());
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
            <DocumentDisplay editable={editable} type={type} contract={changedContract} setChangedContract={setChangedContract}/>
            </div>
            <div className="me-10 flex justify-end gap-10">
            <button className="secondary-button" onClick={e => discard()}>Discard changes</button>
            <button className="primary-button" onClick={e => confrim()}>Confirm</button>
            </div>
        </div>
    )
};