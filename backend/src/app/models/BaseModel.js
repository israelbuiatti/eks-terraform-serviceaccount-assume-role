export default class BaseModel {

    static create(params) {
        let model = {}

        for (const [key, value] of Object.entries(params)) {
            if (this.attrs.includes(key)) {
                model[key] = value;
            }
        }

        return model;
    }

}