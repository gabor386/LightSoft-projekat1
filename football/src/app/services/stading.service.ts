import { Http} from '@angular/http';
import { Injectable } from '@angular/core';
import { DataService } from './data.service';


@Injectable()
export class StadingService extends DataService {

  constructor(http: Http) {
    super('http://localhost:8082/Football/statding-bean', http);
  }
}
