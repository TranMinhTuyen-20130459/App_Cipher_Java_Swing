package services.ma_hoa_doi_xung;

import java.util.Random;

/**
 * Dưới đây là mô tả chi tiết về cách hoạt động cơ bản của thuật toán Vigenère:
 * <p>
 * B1: Chuẩn bị bảng chữ cái: Trước hết, bạn cần chuẩn bị một bảng chữ cái.
 * Thường, trong trường hợp tiếng Anh, bảng này bao gồm 26 chữ cái từ A đến Z (hoặc a đến z). Ví dụ:
 * <p>
 * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 * <p>
 * B2: Chọn khóa: Bạn chọn một chuỗi ký tự làm khóa. Đây là yếu tố quan trọng của thuật toán Vigenère.
 * Khóa có thể có bất kỳ độ dài nào và được tạo ra theo nhu cầu.
 * Ví dụ, khóa có thể là "KEY", "SECRET", hoặc bất kỳ chuỗi nào bạn muốn.
 * <p>
 * B3: Mã hóa dữ liệu văn bản: Để mã hóa, bạn di chuyển qua từng ký tự trong dữ liệu văn bản cần mã hóa và tương ứng nó với ký tự tương ứng trong khóa.
 * Ví dụ, nếu bạn muốn mã hóa ký tự "P" và khóa là "KEY", bạn sẽ tương ứng "P" với "K" (ký tự đầu tiên trong khóa).
 * Sau đó, bạn thực hiện một phép tính trên bảng chữ cái (thêm hoặc trừ vị trí) để tạo ra ký tự đã mã hóa.
 * <p>
 * Ví dụ:
 * <p>
 * Vị trí của "P" trong bảng chữ cái là 15.
 * Vị trí của "K" trong bảng chữ cái là 10.
 * Phép cộng: (15 + 10) % 26 = 25.
 * Ký tự đã mã hóa là "Z".
 * => Tiếp tục quá trình này cho tất cả các ký tự trong dữ liệu văn bản để tạo ra một chuỗi ký tự đã mã hóa.
 * <p>
 * B4: Giải mã dữ liệu đã mã hóa: Để giải mã, bạn sử dụng cùng một khóa để thực hiện quá trình ngược lại.
 * Bạn lặp qua từng ký tự trong dữ liệu đã mã hóa và trừ nó đi với ký tự tương ứng trong khóa.
 * Kết quả là ký tự ban đầu trong dữ liệu văn bản gốc.
 * <p>
 * - Ví dụ:
 * Bạn có ký tự đã mã hóa là "Z" và khóa là "KEY".
 * Vị trí của "Z" trong bảng chữ cái là 25.
 * Vị trí của "K" trong bảng chữ cái là 10.
 * Phép trừ: (25 - 10) % 26 = 15.
 * Ký tự ban đầu là "P".
 * Lặp lại khóa: Khi bạn mã hóa hoặc giải mã dữ liệu, bạn lặp lại khóa nhiều lần nếu cần thiết để có đủ ký tự để tương ứng với tất cả các ký tự trong dữ liệu. Nếu khóa ngắn hơn dữ liệu, bạn lặp lại nó từ đầu.
 * <p>
 * - Ví dụ:
 * Dữ liệu văn bản: "HELLO"
 * Khóa: "KEY"
 * Mã hóa: "HELLO" -> "RIJVS"
 * Giải mã: "RIJVS" -> "HELLO"
 */
public class Cipher_Vigenere_English {
    private static final String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private String key;

    public String createKeyRandom(int key_size) throws Exception {

        if (key_size <= 0) throw new Exception("Key size must be >0");

        Random random = new Random();
        String characters = ALPHABET_UPPERCASE + ALPHABET_LOWERCASE;
        int length_string_random = random.nextInt(1, key_size);
        StringBuilder randomString = new StringBuilder(length_string_random);

        for (int i = 0; i < length_string_random; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        key = randomString.toString();
        return key;
    }

    public String encrypt(String plain_text) throws Exception {
        if (key == null || key.isEmpty()) createKeyRandom(plain_text.length());

        StringBuilder encrypted_text = new StringBuilder();
        int key_index = 0; // Biến đếm vị trí trong chuỗi key
        for (int i = 0; i < plain_text.length(); i++) {
            char plain_char = plain_text.charAt(i);
            char key_char = key.charAt(key_index); // Sử dụng keyChar tại vị trí key_index

            // TH: Nếu là kí tự chữ cái hoa trong bảng chữ cái tiếng Anh
            if (ALPHABET_UPPERCASE.contains(String.valueOf(plain_char))) {
                int plain_index = ALPHABET_UPPERCASE.indexOf(plain_char);
                int key_char_index = ALPHABET_UPPERCASE.indexOf(Character.toUpperCase(key_char));
                int encrypted_index = (plain_index + key_char_index) % ALPHABET_UPPERCASE.length();
                encrypted_text.append(ALPHABET_UPPERCASE.charAt(encrypted_index));
            }
            // TH: Nếu là kí tự chữ kí thường trong bảng chữ cái tiếng Anh
            else if (ALPHABET_LOWERCASE.contains(String.valueOf(plain_char))) {
                int plain_index = ALPHABET_LOWERCASE.indexOf(plain_char);
                int key_char_index = ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(key_char));
                int encrypted_index = (plain_index + key_char_index) % ALPHABET_LOWERCASE.length();
                encrypted_text.append(ALPHABET_LOWERCASE.charAt(encrypted_index));
            } else {
                // Kí tự không thuộc bảng chữ cái tiếng Anh, giữ nguyên
                encrypted_text.append(plain_char);
            }

            // Tăng biến đếm vị trí trong chuỗi key, lặp lại nếu cần
            key_index = (key_index + 1) % key.length();
        }
        return encrypted_text.toString();
    }

    public String decrypt(String encrypted_text) throws Exception {
        if (key == null || key.isEmpty()) throw new Exception("Key is null or empty");

        StringBuilder decrypted_text = new StringBuilder();
        int key_index = 0; // Biến đếm vị trí trong chuỗi key
        for (int i = 0; i < encrypted_text.length(); i++) {

            char encrypt_char = encrypted_text.charAt(i);
            char key_char = key.charAt(key_index); // Sử dụng keyChar tại vị trí keyIndex

            // TH: Nếu là kí tự chữ cái hoa trong bảng chữ cái Tiếng Anh
            if (ALPHABET_UPPERCASE.contains(String.valueOf(encrypt_char))) {
                int encrypt_index = ALPHABET_UPPERCASE.indexOf(encrypt_char);
                int key_char_index = ALPHABET_UPPERCASE.indexOf(Character.toUpperCase(key_char));
                int decrypted_index = (encrypt_index - key_char_index + ALPHABET_UPPERCASE.length()) % ALPHABET_UPPERCASE.length();
                decrypted_text.append(ALPHABET_UPPERCASE.charAt(decrypted_index));
            }
            // TH: Nếu là kí tự chữ cái thường trong bảng chữ cái Tiếng Anh
            else if (ALPHABET_LOWERCASE.contains(String.valueOf(encrypt_char))) {
                int encrypt_index = ALPHABET_LOWERCASE.indexOf(encrypt_char);
                int key_char_index = ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(key_char));
                int decrypted_index = (encrypt_index - key_char_index + ALPHABET_LOWERCASE.length()) % ALPHABET_LOWERCASE.length();
                decrypted_text.append(ALPHABET_LOWERCASE.charAt(decrypted_index));
            } else {
                // Kí tự không thuộc bảng chữ cái tiếng Anh, giữ nguyên
                decrypted_text.append(encrypt_char);
            }

            // Tăng biến đếm vị trí trong chuỗi key, lặp lại nếu cần
            key_index = (key_index + 1) % key.length();
        }
        return decrypted_text.toString();
    }

    public void encryptFile(String srcFile, String destFile) throws Exception {
    }

    public void decryptFile(String srcFile, String destFile) throws Exception {
    }

    public String exportKey() {
        if (key == null) return "";
        return key;
    }

    public String importKey(String key_input) throws Exception {
        if (key_input == null || key_input.isEmpty()) throw new Exception("Key Invalid");
        key = key_input;
        return key;
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "Sam Bankman-Fried, the tousle-haired mogul who founded the FTX cryptocurrency exchange, was convicted on Thursday of seven charges of fraud and conspiracy after a monthlong trial that laid bare the rampant hubris and risk-taking across the crypto industry.\n" +
                "\n" +
                "Mr. Bankman-Fried became a symbol of crypto’s excesses last year when FTX collapsed and he was charged with stealing as much as $10 billion from customers to finance political contributions, venture capital investments and other extravagant spending. A jury of nine women and three men took just over four hours of deliberation on Thursday to reach a verdict, convicting Mr. Bankman-Fried of wire fraud, conspiracy and money laundering.\n" +
                "\n" +
                "Together the counts carry a maximum sentence of 110 years. Mr. Bankman-Fried, 31, is expected to appeal. He’s scheduled to be sentenced on March 28.\n" +
                "\n" +
                "Before the verdict was announced, Mr. Bankman-Fried, wearing a gray suit and purple tie, stood to face the jury, with his hands clasped in front of him. He showed little visible emotion as a juror repeated the word “guilty” seven times. He then took his seat, with his head angled down.\n" +
                "\n" +
                "Mr. Bankman-Fried’s mother, Barbara Fried, put her head in her hands and stifled a sob. Then she and Mr. Bankman-Fried’s father, Joe Bankman, stood arm in arm, separated from their son by a short wooden barrier. As Mr. Bankman-Fried left the room, accompanied by a U.S. marshal, he nodded at his parents, before quickly turning his face away.\n" +
                "\n" +
                "The verdict capped one of the fastest and most spectacular falls from grace in modern corporate history. Just a year ago, Mr. Bankman-Fried was worth more than $20 billion and hailed as a rare good guy in the freewheeling crypto industry, his face plastered on billboards and magazine covers. FTX, valued at $32 billion at its peak, was one of the world’s biggest marketplaces for people to buy and sell digital coins like Bitcoin and Ether.\n" +
                "\n" +
                "Crypto enthusiasts, many of whom openly rooted for Mr. Bankman-Fried to be found guilty, had said they hoped his conviction would provide a moment of catharsis that would allow the industry to move on from a scandal-plagued year. But critics cast the verdict as a sign that the industry may face more legal consequences as it struggles to regain public trust.\n" +
                "\n" +
                "“Perpetrators of scams will have to face the law and suffer the consequences of their crimes, even in crypto,” said Cory Klippsten, the founder of the Swan Bitcoin financial services firm and a frequent critic of the industry. “The ‘Wild West’ days are over.”\n" +
                "\n" +
                "The swift verdict reflected the overwhelming evidence that prosecutors marshaled against Mr. Bankman-Fried, including millions of pages of internal messages, spreadsheets and memos.\n" +
                "\n" +
                "“These guilty verdicts must have been easy decisions for the jurors based on how quickly they returned them,” said John Fishwick, a former U.S. attorney for the Western District of Virginia.\n" +
                "\n" +
                "Mr. Bankman-Fried was always expected to face an uphill battle in court. After FTX imploded, three of his top deputies pleaded guilty to fraud and agreed to cooperate with prosecutors in return for leniency. During the trial, they testified that Mr. Bankman-Fried had repeatedly directed them to lie to the public and route billions of dollars in customer money from FTX to its sister trading firm, Alameda Research.\n" +
                "\n" +
                "Mr. Bankman-Fried’s lawyers argued that he had operated his businesses in good faith and never intended to break the law. But they struggled to poke significant holes in the cooperators’ stories, interrupted by wave after wave of government objections. When Mr. Bankman-Fried took the stand to defend himself, he often seemed flustered, claiming numerous times that he couldn’t remember potentially incriminating conversations.\n" +
                "\n" +
                "Mark Cohen, Mr. Bankman-Fried’s lawyer, said in a statement that the defense team respected the jury’s verdict. But he added that Mr. Bankman-Fried “maintains his innocence and will continue to vigorously fight the charges against him.”\n" +
                "\n" +
                "In a news conference outside the courthouse, Damian Williams, the top federal prosecutor in Manhattan, said Mr. Bankman-Fried had “perpetrated one of the biggest financial frauds in American history.”\n" +
                "\n" +
                "“The crypto industry might be new,” he said. “But this kind of fraud, this kind of corruption, is as old as time.”\n" +
                "\n" +
                "Mr. Bankman-Fried rose to prominence by marketing himself as an unusual sort of billionaire — a force for good who accumulated wealth in the hopes of eventually giving it all away. He founded FTX in 2019, and raised billions of dollars from investors to turn it into one of the world’s leading crypto companies.\n" +
                "\n" +
                "On trips to Washington and Los Angeles, he hobnobbed with politicians and movie stars, and made tens of millions of dollars in campaign contributions to both Democrats and Republicans. During his ascent, business partners likened him to John Pierpont Morgan, the pioneering banker who once dominated the finance industry.\n" +
                "\n" +
                "Then Mr. Bankman-Fried’s business empire collapsed over a matter of days last November, when a run on deposits exposed an $8 billion hole in FTX’s accounts. (Thursday’s verdict arrived one year to the day after the publication of a leaked Alameda balance sheet set off that crisis.) FTX soon filed for bankruptcy, and Mr. Bankman-Fried stepped down as chief executive. In December, he was arrested at his home in the Bahamas, where FTX had its headquarters.\n" +
                "\n" +
                "Mr. Bankman-Fried tried to dismiss FTX’s collapse as the unfortunate result of a monumental accounting error, rather than a deliberate fraud. But at his trial, prosecutors argued that he had repeatedly lied to customers, lenders and investors, using their funds to build himself up into a crypto titan.\n" +
                "\n" +
                "“This was a fraud that occurred on a massive scale,” Nicolas Roos, one of the federal prosecutors, said in the government’s closing argument. “Thousands of people lost billions of dollars.”\n" +
                "\n" +
                "During the trial, the government called more than a dozen witnesses, including the three cooperators, who had lived with Mr. Bankman-Fried in a palatial, five-bedroom penthouse in the Bahamas that the government claimed was purchased using FTX customer money.\n" +
                "\n" +
                "Gary Wang, a co-founder of FTX, testified that Mr. Bankman-Fried had instructed him to create a secret back door in the exchange’s code that allowed Alameda to borrow a virtually limitless amount of customer funds. Nishad Singh, another top FTX executive, said Mr. Bankman-Fried had spent lavishly on investments and endorsement deals, even after he knew that customer accounts were in peril.\n" +
                "\n" +
                "The most emotional moment of the prosecution’s case came during the testimony of Caroline Ellison, the chief executive of Alameda and Mr. Bankman-Fried’s on-and-off girlfriend. Over three days on the witness stand, Ms. Ellison said she had conspired with Mr. Bankman-Fried to mislead the public and doctor balance sheets that she sent to lenders.\n" +
                "\n" +
                "Fighting back tears, Ms. Ellison said the collapse of FTX had been strangely cathartic. “I felt this sense of relief that I didn’t have to lie anymore,” she said, “and that I could start taking responsibility even though I felt indescribably bad.”\n" +
                "\n" +
                "Ms. Ellison, Mr. Wang and Mr. Singh, who all pleaded guilty to fraud, are scheduled to be sentenced in late 2024.\n" +
                "\n" +
                "The trial reached a climax late last month when Mr. Bankman-Fried took the stand. He insisted that he had never intended to commit crimes and had just wanted to build a successful company. But on cross-examination, Danielle Sassoon, a lead prosecutor, exposed cracks in his story, showing the contradictions between his public statements and how he behaved in private.\n" +
                "\n" +
                "Even after the verdict, Mr. Bankman-Fried’s legal battle is likely to continue. He is tentatively scheduled for a second trial on campaign finance and other charges early next year, though it’s unclear whether it will take place. On Thursday night, Judge Lewis A. Kaplan, who oversaw the trial, asked prosecutors to give him an update by February on the potential second trial.\n" +
                "\n" +
                "Some restrictions Mr. Bankman-Fried and his lawyers have faced in court could become fodder for an appeal. Before the trial, Judge Kaplan issued a series of rulings that limited what Mr. Bankman-Fried’s lawyers could argue in front of the jury. They were prevented from calling several expert witnesses, and blocked from claiming that FTX’s lawyers reviewed many of Mr. Bankman-Fried’s actions as chief executive.\n" +
                "\n" +
                "Mr. Bankman-Fried also spent the final weeks before his trial in jail after Judge Kaplan revoked his bail, ruling that he had tried to intimidate witnesses.\n" +
                "\n" +
                "Before the courtroom emptied late Thursday, Judge Kaplan offered a few final remarks, avoiding any direct comments about the case.\n" +
                "\n" +
                "He thanked the jury for its work and praised the lawyers on both sides. “A good job all around,” he said, and left the room.";

        Cipher_Vigenere_English vigenere = new Cipher_Vigenere_English();
        String encryptedText = "";
        String decryptedText = "";

//        for (int i = 0; i < 1000; i++) {
//            encryptedText = vigenere.encrypt(plaintext);
//            System.out.println("Encrypted: " + encryptedText);
//            System.out.println("Key:" + vigenere.exportKey());
//            vigenere.key = null;
//            System.out.println("----------------------------------------------------");
//        }

        vigenere.importKey("TRANMINHTUYEN");
        encryptedText = vigenere.encrypt(plaintext);
        System.out.println("Key: " + vigenere.exportKey());
        System.out.println("----------------------------------------------------");
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("----------------------------------------------------");
        decryptedText = vigenere.decrypt(encryptedText);
        System.out.println("Decrypted: " + decryptedText);
        System.out.println("----------------------------------------------------");
        System.out.println("Check Decrypted: " + plaintext.equals(decryptedText));
    }
}
