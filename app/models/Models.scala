package models

import java.util.Date
import org.squeryl.{Schema, KeyedEntity}
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import org.squeryl.annotations.Column


case class Role(@Column("role_id") roleId: Int,
                @Column("role_name") roleName: String) extends KeyedEntity[Int] {
  override def id = roleId
}

case class User(@Column("user_id") userId: String,
                password: String,
                @Column("full_name") fullName: String,
                @Column("role_id") roleId: String,
                email: String) extends KeyedEntity[String] {
  override def id = userId
}

case class Bug(id: Int,
               assignee: String,
               summary: String,
               @Column("open_date") openDate: Date,
               @Column("due_date") dueDate: Date,
               @Column("close_date") closeDate: Date) extends KeyedEntity[Int] {
  lazy val details: OneToMany[Detail] = MyDB.bugToDetails.left(this)
}

case class Detail(@Column("bug_id") bugId: Int,
                  @Column("det_id") detId: Int,
                  @Column("det_date") detDate: Date,
                  @Column("user_id") userId: String,
                  comment: String) extends KeyedEntity[CompositeKey2[Int, Int]] {
  override def id = compositeKey(bugId, detId)
  lazy val bug: ManyToOne[Bug] = MyDB.bugToDetails.right(this)
}

object MyDB extends Schema {
  val rolesTable = table[Role]("role")
  val usersTable = table[User]("user")
  val bugsTable = table[Bug]("bug")
  val detailsTable = table[Detail]("detail")

  on(bugsTable) {bug => declare {
    bug.id is(primaryKey, autoIncremented)
  }}

  val bugToDetails = oneToManyRelation(bugsTable, detailsTable).via((bug, det) => bug.id === det.bugId)
}

object Role {
  import MyDB.rolesTable

  private def allRolesQuery = from(rolesTable) {role => select(role) orderBy(role.roleName asc)}


  def getAllRoles: List[Role] = inTransaction( allRolesQuery.toList )
  def insert(role: Role): Role = inTransaction( rolesTable.insert(role.copy()))
  def update(role: Role) { inTransaction( rolesTable.update(role)) }

}

object User {
  import MyDB.usersTable

  private def allUsersQuery = from(usersTable) {user => select(user) orderBy(user.id)}
  private def findByIdQuery(id: String) = from(usersTable) {user => where(user.userId === id) select(user)}

  def getAllUsers: List[User] = inTransaction( allUsersQuery.toList )
  def findById(id: String): Option[User] = inTransaction( findByIdQuery(id).headOption )

  def insert(user: User) = inTransaction( usersTable.insert(user.copy()) )
  def update(user: User) { inTransaction( usersTable.update(user)) }

  def authenticate(id: String, password: String): Option[User] = findById(id).filter(user => user.password == password)

}

object Bug {
  import MyDB.bugsTable

  private def allBugsQuery = from(bugsTable) {bug => select(bug) orderBy(bug.id)}
  private def oneBugQuery(id: Int) = from(bugsTable) { bug => where(bug.id === id) select(bug)}

  def getAllBugs = inTransaction( allBugsQuery.toList )
  // what do we return if the bug is not found?
  def getOneBug(id: Int): Bug = inTransaction ( oneBugQuery(id).head )
  def insert(bug: Bug) = inTransaction ( bugsTable.insert(bug.copy()))
  def update(bug: Bug) { inTransaction ( bugsTable.update(bug)) }
}




