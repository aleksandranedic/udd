import React, { ChangeEvent, useState } from "react";
import { ParameterEqualityOperand, ParameterField, ParameterObject, ParameterOperand } from "../types/pages";
import { TbMinus, TbPlus } from "react-icons/tb";
import { useAppDispatch } from "../store/types";
import { advancedSearch } from "../store/actions/contract-actions";
import { SearchResults } from "../components/SearchResults";

export const AdvancedSearch: React.FunctionComponent = () => {
    const [parameters, setParameters] = useState([new ParameterObject(ParameterField.EMPLOYEE_NAME, ParameterEqualityOperand.IS, '', false)]);
    const dispatch = useAppDispatch();
    const setField = (value: string, parameter: ParameterObject) => {
        parameter.field = value as ParameterField;
        setParameters([...parameters]);
    };
    const setOperand = (value: string, parameter: ParameterObject) => {
        parameter.operator = value as ParameterOperand;
        setParameters([...parameters]);
    };
    const setEqualityOperand = (value: string, parameter: ParameterObject) => {
        parameter.equalityOperand = value as ParameterEqualityOperand;
        setParameters([...parameters]);
    };
    const setIsPhase = (e: ChangeEvent<HTMLInputElement>, parameter: ParameterObject) => {
        parameter.phrase = (e.target as HTMLInputElement).checked;
        setParameters([...parameters]);
    };

    const setValue = (value: string, parameter: ParameterObject) => {
        parameter.value = value;
        setParameters([...parameters]);
    }

    const add = () => {
        setParameters([...parameters, new ParameterObject(ParameterField.EMPLOYEE_NAME, ParameterEqualityOperand.IS, '', false, ParameterOperand.AND)]);
    }
    const remove = (index: number) => {
        parameters.splice(index, 1)
        setParameters([...parameters]);
    }

    const search = () => {
        dispatch(advancedSearch(parameters));
    }

    return (
        <div className="w-full flex flex-col h-full">
            <div className="h-2/5 pe-8 gap-5 flex flex-col overflow-auto">
                <span className="font-extralight mb-12 block">
                    Search contract and law content <strong> by combining</strong> parameters
                </span>
               {parameters.map((parameter, index) => 
               <React.Fragment key={index}>
                <div className="flex w-full justify-between px-10">
                    <div className="flex gap-10 w-[70%]">
                        {index > 0 && <select onChange={e => setOperand(e.target.value, parameter)}>
                            {Object.values(ParameterOperand).map((operand) => <option value={operand}>{operand}</option>)}
                        </select>}
                        {index === 0 && <div className="w-[70px]"></div>}
                        <select className="w-72" value={parameter.field} onChange={e => setField(e.target.value, parameter)}>
                            {Object.values(ParameterField).map((field) => <option value={field}>{field}</option>)}
                        </select>
                        <select className="w-20" value={parameter.equalityOperand} onChange={e => setEqualityOperand(e.target.value, parameter)}>
                            {Object.values(ParameterEqualityOperand).map((field) => <option value={field}>{field}</option>)}
                        </select>
                        <input type="text" className="w-72" placeholder="Enter value" value={parameter.value} onChange={e => setValue(e.target.value, parameter)}/>
                        {index > 0 && <div className="w-20"></div>}
                    </div>
                    <div className="w-[20%] flex justify-end gap-4 self-end">
                        <input type="checkbox" checked={parameter.phrase} id="isPhrase" className="accent-secondary text-off-white" onChange={e => setIsPhase(e, parameter)}/>
                        <label htmlFor="isPhrase">Phrase</label>
                    </div>
                    <div className="flex gap-1 w-[10%] justify-end">
                        {index > 0 && <button className="secondary-round-button" onClick={e => remove(index)}>
                            <TbMinus />
                        </button>}
                        <button className="secondary-round-button" onClick={e => add()}>
                            <TbPlus />
                        </button>
                    </div>
                </div>
                </React.Fragment>)}
                <button className="primary-button self-end mt-10" onClick={() => search()}>Search</button>
            </div>
            <div className="h-4/5 overflow-auto pt-10">
                <SearchResults/>
            </div>
        </div>
    )
};