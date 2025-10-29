using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema; // <-- important

namespace CompteDepot.Models
{
    [Table("client")] // 👈 correspond exactement au nom dans ta base PostgreSQL
    public class Client
    {
        [Key]  // 👈 Indique explicitement la clé primaire
        [Column("idclient")] // 👈 nom exact dans ta base
        public int IdClient { get; set; }

        [Required]
        [MaxLength(50)]
        [Column("nom")] // 👈 nom exact dans ta base
        public required string Nom { get; set; }
    }
}
