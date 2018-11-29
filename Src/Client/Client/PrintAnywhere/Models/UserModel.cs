
namespace PrintAnywhere.Models
{
    public class UserModel
    {
        public User user { get; set; }
    }
    public class User
    {
        public string userId { get; set; }
        public string userPw { get; set; }
        public string userName { get; set; }
        public string userNickName { get; set; }
        public string userMail { get; set; }
        public string userPhonenumber { get; set; }
        public string userPoint { get; set; }

    }
}
