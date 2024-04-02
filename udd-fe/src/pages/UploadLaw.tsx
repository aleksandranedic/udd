import * as TbIcons from 'react-icons/tb'

export const UploadLaw: React.FunctionComponent = () => {
    return (
        <div className="w-full flex flex-col h-full">
            <div className="h-full pe-8 gap-5 flex flex-col">
                <span className="font-extralight mb-12 block">
                    <strong>Upload</strong> law and <strong> save </strong> it in order to <strong>search its content</strong>
                </span>
                <div className="flex w-full relative items-end">
                    {TbIcons.TbUpload({size:20, style:{fontWeight: 200, marginBottom:'5px'}})}
                    <label className="w-4/5 custom-file-upload me-16">
                        <input type="file" className="w-full hidden" />
                        Click to upload law
                    </label>
                    <button className="primary-button">Save law</button>
                </div>
            </div>
        </div>
    );
}