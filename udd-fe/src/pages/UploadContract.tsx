import * as TbIcons from 'react-icons/tb'
import { useAppDispatch } from '../store/types';
import { useRef, useState } from 'react';
import { submitContract } from '../store/actions/contract-actions';
import { ContractComp } from '../components/Contract';
import { DocumentType } from '../types/document';

export const UploadContract: React.FunctionComponent = () => {
    const [inputFileLabel, setInputFileLabel] = useState('Click to upload contract');
    const fileContainerRef = useRef<HTMLInputElement>(null);
    const dispatch = useAppDispatch();

    const saveContract = async () => {
        const file = fileContainerRef.current?.files?.item(0);
        if (!file) return;
        await dispatch(submitContract(file));
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
                    <strong>Upload</strong> contract in specific <a  href="http://localhost:3000/contract-form.png" target="_blank" rel="noreferrer">form</a> and <strong> save </strong> it in order to <strong>search its content</strong>
                </span>
                <div className="flex w-full relative items-end">
                    {TbIcons.TbUpload({size:20, style:{fontWeight: 200, marginBottom:'5px'}})}
                    <label className="w-4/5 custom-file-upload me-16">
                        <input type="file" className="w-full hidden" accept="application/pdf" ref={fileContainerRef} onChange={e => setLabel()} />
                        {inputFileLabel}
                    </label>
                    <button className="primary-button" onClick={e => saveContract()}>Save contract</button>
                </div>
            </div>

            <ContractComp editable type={DocumentType.Contract}/>
        </div>
    );
}