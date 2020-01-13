import { Http} from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';



@Injectable()
export class DataService {

  constructor(private url: string , private http: Http) { }

  getAll() {
    return this.http.get(this.url)
    .map( response => response.json());
  }

  getById(id: any) {
    console.log(this.url+id);
    console.log(this.http.get(this.url+id).map(response => response.json()));
    return this.http.get(this.url+id)
    .map( response => response.json());
    
  }




}
