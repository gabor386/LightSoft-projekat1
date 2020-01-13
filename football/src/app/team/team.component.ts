import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  team:any;

  constructor(private service:TeamService) { }

  ngOnInit() {
    this.service.getAll()
    .subscribe(team=> {this.team=team;
    console.log(this.team)});
  }

}
