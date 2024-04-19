import { useRef, useState } from 'react';
import * as TbIcons from 'react-icons/tb'
import { useAppDispatch } from '../store/types';
import { submitLaw } from '../store/actions/contract-actions';
import { ContractComp } from '../components/Contract';
import { DocumentType } from '../types/document';

export const UploadLaw: React.FunctionComponent = () => {
    const [inputFileLabel, setInputFileLabel] = useState('Click to upload law');
    const fileContainerRef = useRef<HTMLInputElement>(null);
    const dispatch = useAppDispatch();

    const saveLaw = async () => {
        const file = fileContainerRef.current?.files?.item(0);
        if (!file) return;
        await dispatch(submitLaw(file));
    };

    const setLabel = () => {
        const fileName = fileContainerRef.current?.files?.item(0)?.name;
        if (fileName) {
            setInputFileLabel(fileName);
        }  
    }
    return (
         <div className="w-full flex flex-col h-full">
            <div className="pe-8 gap-5 flex flex-col">
                <span className="font-extralight mb-12 block">
                    <strong>Upload</strong> law and <strong> save </strong> it in order to <strong>search its content</strong>
                </span>
                <div className="flex w-full relative items-end">
                    {TbIcons.TbUpload({size:20, style:{fontWeight: 200, marginBottom:'5px'}})}
                    <label className="w-4/5 custom-file-upload me-16">
                        <input type="file" className="w-full hidden" ref={fileContainerRef} onChange={e => setLabel()}/>
                        {inputFileLabel}
                    </label>
                    <button className="primary-button" onClick={e => saveLaw()}>Save law</button>
                </div>
            </div>
            <ContractComp editable type={DocumentType.Law}/>
        </div>
    );
}