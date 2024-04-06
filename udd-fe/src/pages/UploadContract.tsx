import * as TbIcons from 'react-icons/tb'
import { Contract } from '../types/document';
import { useAppSelector } from '../store/types';
import { useRef } from 'react';
import { submitContract } from '../store/actions/contract-actions';

export const UploadContract: React.FunctionComponent = () => {
    const submittedContract: Contract | null = useAppSelector((state) => state.contractSlice.contract);
    const fileContainerRef = useRef<HTMLInputElement>(null);

    const saveContract = async () => {
        const file = fileContainerRef.current?.files?.item(0);
        if (!file) return;
        submitContract(file);
    };
    return (
        <div className="w-full flex flex-col h-full">
            <div className="h-full pe-8 gap-5 flex flex-col">
                <span className="font-extralight mb-12 block">
                    <strong>Upload</strong> contract in specific <a  href="http://localhost:3000/contract-form.png" target="_blank" rel="noreferrer">form</a> and <strong> save </strong> it in order to <strong>search its content</strong>
                </span>
                <div className="flex w-full relative items-end">
                    {TbIcons.TbUpload({size:20, style:{fontWeight: 200, marginBottom:'5px'}})}
                    <label className="w-4/5 custom-file-upload me-16">
                        <input type="file" className="w-full hidden" ref={fileContainerRef} />
                        Click to upload contract
                    </label>
                    <button className="primary-button" onClick={e => saveContract()}>Save contract</button>
                </div>
            </div>

            <div>
                {submittedContract === null ? 'aaa' : JSON.stringify(submittedContract)};
            </div>
        </div>
    );
}