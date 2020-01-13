import { Injectable } from '@angular/core';
import { DataService } from './data.service';
import { Http} from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class TeamService extends DataService{

  constructor(http: Http) {
    super('http://localhost:8082/Football/team/', http);
 }
}
