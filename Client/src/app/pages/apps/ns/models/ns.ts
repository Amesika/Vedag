export class Ns {
	

    id: number;
    name: string;
    description: string;

    constructor(){
        
    }

    setProperies(namespace: any) {
		this.id = namespace.id;
        this.name = namespace.name;
        this.description = namespace.description;
	}

}