import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TeamService } from '../services/team.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {
  idTeam : any;
  team:any;

  constructor(private service:TeamService, private route: ActivatedRoute) { }

  ngOnInit() {
  
    this.route.queryParams
    .filter(params => params.teamID)
    .subscribe(params => {
      this.idTeam = params.teamID;
      this.service.getById(this.idTeam)
      .subscribe( team=> {this.team=team;
        console.log(this.team)});
    });

  }

}
